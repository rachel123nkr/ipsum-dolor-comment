package controllers;
import play.mvc.*;
import play.libs.ws.*;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;


import utils.CommentsApi;

public class HomeController extends Controller implements WSBodyReadables, WSBodyWritables{
    private final CommentsApi cws;


    @Inject
    public HomeController(CommentsApi cws) {
        this.cws = cws;
    }


    public CompletionStage<Result> index(Http.Request request) {
        String[] postIds = request.queryString().get("postId");
        if(postIds == null || postIds.length<=0 )
            return CompletableFuture.supplyAsync(() -> ok(views.html.index.render(null)));

        try {
            Integer postId = Integer.parseInt(postIds[0]);
            return cws.getComments(postId).thenApplyAsync(commentList -> {
                if (commentList == null)
                    return ok(views.html.error.render("Data Access Error"));
                return ok(views.html.index.render(commentList));
            });
        }
        catch (NumberFormatException nfe){
            return CompletableFuture.supplyAsync(() ->ok(views.html.index.render(null)));
        }
        catch (Exception ex){
            return CompletableFuture.supplyAsync(() ->badRequest(views.html.error.render(ex.getMessage())));

        }
    }

}

