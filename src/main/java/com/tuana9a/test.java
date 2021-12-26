package com.tuana9a;

import com.tuana9a.service.SearchEngineImpl;
import com.tuana9a.service.SearchEngineService;
import lombok.AllArgsConstructor;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@AllArgsConstructor
public class test {

    public static void main(String[] args) throws IOException, ParseException {
        SearchEngineImpl searchEngine = new SearchEngineImpl();
        System.out.println(searchEngine.searchDocument("html", 100).size());
    }
}
