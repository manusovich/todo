package com.mashape.testtask.todo.plugin.searchbox;

import com.mashape.testtask.todo.api.exception.IndexingException;
import com.mashape.testtask.todo.api.model.TodoEntity;
import com.mashape.testtask.todo.api.service.IndexingService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Elastic search Indexing implementation
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
@Component
public class IndexingServiceImpl implements IndexingService {
    private static final String INDEX_NAME = "mashapetodo";
    private static final String TODO_TYPE = "todo";

    @Autowired
    JestClient jestClient;

    @Override
    public void index(TodoEntity todoEntity) throws IndexingException {
        Index index = new Index.Builder(todoEntity)
                .index(INDEX_NAME)
                .type(TODO_TYPE)
                .id(todoEntity.getId())
                .build();
        try {
            jestClient.execute(index);
        } catch (Exception e) {
            throw new IndexingException(e.getMessage());
        }
    }

    @Override
    public void updateIndex(TodoEntity todoEntity) throws IndexingException {
        Index index = new Index.Builder(todoEntity)
                .index(INDEX_NAME)
                .type(TODO_TYPE)
                .id(todoEntity.getId())
                .build();
        try {
            jestClient.execute(index);
        } catch (Exception e) {
            throw new IndexingException(e.getMessage());
        }
    }

    /**
     * We have to search first of all in titles and after that in bodies
     *
     * @param query String for search
     * @return List of TodoEntity
     * @throws IndexingException
     */
    @Override
    public List<TodoEntity> search(String query) throws IndexingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(
                QueryBuilders.boolQuery()
                        .should(QueryBuilders.matchQuery(TodoEntity.TITLE, query))
                        .should(QueryBuilders.matchQuery(TodoEntity.BODY, query)));

        Search search = new Search.Builder(searchSourceBuilder.toString())
                .addIndex(INDEX_NAME)
                .addType(TODO_TYPE)
                .build();

        try {
            JestResult result = jestClient.execute(search);
            return result.getSourceAsObjectList(TodoEntity.class);
        } catch (Exception e) {
            throw new IndexingException(e.getMessage());
        }
    }
}
