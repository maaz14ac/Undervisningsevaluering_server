package view.endpoints;

import com.google.gson.Gson;
import logic.StudentController;
import security.Digester;
import shared.ReviewDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by Kasper on 19/10/2016.
 */

@Path("/api/student")
public class StudentEndpoint extends UserEndpoint {

    /**
     * En metode til at tilføje reviews for en lektion.
     *
     * @param json indeholdende et review.
     * @return boolean.
     */
    @POST
    @Consumes("application/json")
    @Path("/review")
    public Response addReview(String json) {

        Gson gson = new Gson();
        ReviewDTO review = new Gson().fromJson(json, ReviewDTO.class);

        StudentController studentCtrl = new StudentController();
        boolean isAdded = studentCtrl.addReview(review);

        if (isAdded) {
            String toJson = gson.toJson(Digester.encrypt(gson.toJson(isAdded)));

            return successResponse(200, toJson);

        } else {
            return errorResponse(404, "Failed. Couldn't get reviews.");
        }
    }

    /**
     * En metode til at softdelete et review. (Både kommentar og rating)
     *
     * @param data oplysninger på bruger og review der ønskes fjernet.
     * @return boolean.
     */
    @DELETE
    @Consumes("application/json")
    @Path("/review/")
    public Response deleteReview(String data) {
        Gson gson = new Gson();

        ReviewDTO review = gson.fromJson(data, ReviewDTO.class);
        StudentController studentCtrl = new StudentController();

        boolean isDeleted = studentCtrl.softDeleteReview(review.getUserId(), review.getId());

        if (isDeleted) {
            String toJson = gson.toJson(Digester.encrypt(gson.toJson(isDeleted)));

            return successResponse(200, toJson);
        } else {
            return errorResponse(404, "Failed. Couldn't delete the chosen review.");
        }
    }

    /**
     * Nedenstående er OPTIONS metoder udelukkende til at løse CORS-problemstillingen i forbindelse med localhost.
     * Læs mere på: https://developer.mozilla.org/en-US/docs/Web/HTTP/Access_control_CORS
     * */
    @OPTIONS
    @Path("/review")
    public Response optionsAddDeleteReview() {
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .build();
    }

}
