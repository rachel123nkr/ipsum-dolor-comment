package controllers;

import akka.http.javadsl.model.HttpRequest;
import com.fasterxml.jackson.databind.JsonNode;
import models.Comment;
import org.checkerframework.checker.units.qual.C;
import play.libs.Json;
import play.mvc.*;
import play.libs.ws.*;

import javax.inject.Inject;
import javax.validation.constraints.Null;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import utils.Util;

public class HomeController extends Controller implements WSBodyReadables, WSBodyWritables{
    private final WSClient ws;


    @Inject
    public HomeController(WSClient ws) {
        this.ws = ws;
    }


    public Result index(Http.Request request) {
        String[] postIds = request.queryString().get("postId");
        if(postIds == null || postIds.length<=0 )
            return ok(views.html.index.render(null));

        try {
            List<Comment> comm_list =new ArrayList<>();
            Integer postId = Integer.parseInt(postIds[0]);
            JsonNode json_of_comments = new Util(ws).getComments(postId);
            for (JsonNode comm : json_of_comments) {
                comm_list.add(new Comment(comm));
            }
            return ok(views.html.index.render(comm_list));
        }
        catch (NumberFormatException nfe){
            return ok(views.html.index.render(null));
        }
        catch (Exception ex){
            return badRequest(views.html.error.render(ex.getMessage()));

        }
    }

}

