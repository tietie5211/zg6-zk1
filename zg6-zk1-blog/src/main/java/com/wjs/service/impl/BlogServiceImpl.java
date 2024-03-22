package com.wjs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.wjs.Vo.BlogVo;
import com.wjs.config.BlogConfig;
import com.wjs.entity.BlogEntity;
import com.wjs.mapper.BlogMapper;
import com.wjs.param.BlogParam;
import com.wjs.pojo.Blog;
import com.wjs.repository.BlogRepository;
import com.wjs.service.BlogService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

/**
 * description
 *
 * @author wangjunshan
 * @date 2023/11/16 10:05
 */
@Service
@Transactional
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {


    private static final String URL = "http://192.168.171.129/";
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    BlogConfig blogConfig;


    /**
     * 全量
     */
    //用在于
    // @Scheduled(fixedDelay = )
    //用在于  每天的 每个分钟
    @Scheduled(cron = "0/8 * * * * ?")
    public void fushblogList() {
        List<Blog> blogs = blogMapper.selectList(null);
        for (Blog blog : blogs) {
            BlogEntity blogEntity = BeanUtil.toBean(blog, BlogEntity.class);
            blogRepository.save(blogEntity);
        }
        // 建立库名  表明  第一个参数为 库名  第二个参数为 表明
        //库名
        // IndexRequest pinxixi2104 = indexRequest.index("pinxixi2104");
        //表明
        // IndexRequest blog = indexRequest.type("blog");
        // List<Blog> blogs = blogMapper.selectList(null);
        // for (Blog blog : blogs) {
        //     IndexRequest indexRequest = new IndexRequest("pinxixi2104", "blog");
        //
        //     RestHighLevelClient restHighLevelClient = blogConfig.initElasticSearch();
        //     //第一个参数  建立索引的请求    第二位是个常亮  固定的
        //     restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        // }



        // RestHighLevelClient restHighLevelClient = blogConfig.initElasticSearch();、
    }
  /**
     * 增量同步
     */
    @Scheduled(fixedDelay = 60*1000)
    public void increamentElasticSerach() throws IOException {
        RestHighLevelClient restHighLevelClient = blogConfig.initElasticSearch();

        //获取触发的定时器时间  （同步到elasticsearch容器里）触发的时间 用于 判断查询修改（字段时间）后的 数据
        long l = System.currentTimeMillis();
        // 60 秒
        long last = l - 60000;
        // 条件查询  last  用于当前时间小于修改后的时间 之后遍历  同步到 es 容器里
        List<Blog> blogs = blogMapper.selectListByTime(last);
        for (Blog blog : blogs) {

            IndexRequest indexRequest = new IndexRequest();
            // 库名
            indexRequest.index("pinxixi2104");
            //表明
            indexRequest.type("blog");

            Map data = new HashMap();
            data.put("title","");
            data.put("content","");
            data.put("upvoteNum","");
            data.put("transpondNum","");
            data.put("startTime","");
            data.put("updateTime","");
            data.put("userId","");
            data.put("picture","");

            // 第一个为库名  第二个为 表名字
            IndexResponse index = restHighLevelClient.index(null,RequestOptions.DEFAULT);


        }


    }


    @Override
    public Map<String, Object> blogList(BlogVo blogVo) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        // 模糊拆线呢
        if (StringUtils.isNotBlank(blogVo.getTitle())) {
            boolQuery.must(QueryBuilders.matchQuery("title", blogVo.getTitle()));
        }
        if (blogVo.getMin() != null) {
            boolQuery.must(QueryBuilders.rangeQuery("startTime").gte(blogVo.getMin()));
        }
        if (blogVo.getMax() != null) {
            boolQuery.must(QueryBuilders.rangeQuery("startTime").lte(blogVo.getMax()));
        }
        // 排序
        Sort sort = Sort.by("startTime");
        // 分页
        PageRequest pageRequest = PageRequest.of(blogVo.getPageCurrent() - 1, blogVo.getPageSize(), sort);

        HighlightBuilder highlightBuilder = new HighlightBuilder().field("title").preTags("<font color='red'>").postTags("</font>");

        NativeSearchQuery build = new NativeSearchQueryBuilder().withQuery(boolQuery).withPageable(pageRequest).withHighlightBuilder(highlightBuilder).build();

        SearchHits<BlogEntity> search = elasticsearchRestTemplate.search(build, BlogEntity.class);

        List<BlogEntity> list = new ArrayList<>();

        long totalHits = search.getTotalHits();
        for (SearchHit<BlogEntity> blogEntitySearchHit : search) {
            BlogEntity content = blogEntitySearchHit.getContent();
            Map<String, List<String>> highlightFields = blogEntitySearchHit.getHighlightFields();
            Set<Map.Entry<String, List<String>>> entries = highlightFields.entrySet();
            for (Map.Entry<String, List<String>> entry : entries) {
                content.setTitle(entry.getValue().get(0));
            }
            list.add(content);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("total", totalHits);

        return map;
    }

    @Override
    public int add(Blog blog) {

        blog.setUpvoteNum(0);
        blog.setTranspondNum(0);
        int insert = blogMapper.insert(blog);

        // 增量同步
        Blog byId = blogMapper.selectById(blog.getBlogId());
        LocalDateTime updateTime = byId.getUpdateTime();
        scopeList(updateTime);
        return insert;
    }

    /**
     * 增量同步
     * @param up
     */
    public void scopeList(LocalDateTime up){

        List<Blog> blogs = blogMapper.selectList(null);
        for (Blog blog : blogs) {
            // 判断时间是否在修改时间之后 和等于当前的修改时间
            if(up.isAfter(blog.getUpdateTime())||up.equals(blog.getUpdateTime())){
                BlogEntity blogEntity = BeanUtil.toBean(blog, BlogEntity.class);
                blogRepository.save(blogEntity);
            }
        }
    }


    @Override
    public Blog selectOne(Blog blog) {

        Blog blog1 =  blogMapper.selectTitle(blog);

        return blog1;
    }

    @Override
    public int updateUpvote(BlogParam blogParam) {

        blogParam.setNum(blogParam.getNum()+1);
      int i = blogMapper.updateUpvote(blogParam);

        Blog byId = blogMapper.selectById(blogParam.getId());
        LocalDateTime updateTime = byId.getUpdateTime();
        scopeList(updateTime);

        return i;
    }


    @Autowired
    FastFileStorageClient fastFileStorageClient;
    @Override
    public String upload(MultipartFile file) {
        String fullPath = "";
        try {
        String s = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
             fullPath =URL + fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), s, null).getFullPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fullPath;
    }
}
