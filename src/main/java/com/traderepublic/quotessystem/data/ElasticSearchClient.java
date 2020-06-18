package com.traderepublic.quotessystem.data;

import com.traderepublic.quotessystem.clients.messages.Message;
import org.elasticsearch.action.index.IndexRequest;

public class ElasticSearchClient {
    public void index(Message message, String index){
        IndexRequest request = new IndexRequest(index);
    }
}
