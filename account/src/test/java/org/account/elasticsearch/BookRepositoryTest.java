package org.account.elasticsearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.account.AccountApplication;
import org.account.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

@Slf4j
// @Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookRepositoryTest {

    @Resource
    private ObjectMapper mapper;

    @Resource
    private ElasticsearchRestTemplate template;

    @Test
    public void saveAll() {
        List<Book> list = Lists.newArrayList();
        for (int i = 0; i < 500; i++) {
            Book book = new Book();
            book.setAuthor("test" + i);
            book.setId(String.valueOf(i));
            book.setTitle("es test " + i);
            book.setPostDate(Instant.now().toString());
            list.add(book);
        }
        template.save(list);
    }

    @Test
    public void test2() throws JsonProcessingException {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery()).build();
        SearchHits<Book> allBook = template.search(query, Book.class);
        System.out.println(mapper.writeValueAsString(allBook));

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(wildcardQuery("title", "es test 3*")).build();
        SearchHits<Book> hits = template.search(searchQuery, Book.class);
        System.out.println(mapper.writeValueAsString(hits));
    }

}