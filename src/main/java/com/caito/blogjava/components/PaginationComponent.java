package com.caito.blogjava.components;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PaginationComponent {

    public String paginationResponse(Page<?> page){
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.add("content", gson.toJsonTree(page.getContent()));
        json.add("totalPages", gson.toJsonTree(page.getTotalPages()));
        json.add("firstPage", gson.toJsonTree(page.isFirst()));
        json.add("lastPage", gson.toJsonTree(page.isLast()));

        return gson.toJson(json);
    }
}
