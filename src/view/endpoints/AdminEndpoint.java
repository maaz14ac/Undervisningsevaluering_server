package view.endpoints;

import com.google.gson.Gson;
import logic.AdminController;
import security.Digester;
import shared.ReviewDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by Mahroz on 15/12/2016.
 * Klassen er endpoint for en admin-bruger og håndtere API-kald fra en klient.
 */
@Path("/api/admin")
public class AdminEndpoint extends UserEndpoint {
    /**
     * En metode til at slette en kommentar fra en bruger. (Berører ikke selve ratingen)
     * @param data oplysninger på bruger og review der ønskes fjernet.
     * @return boolean.
     */
    @DELETE
    @Consumes("application/json")
    @Path("/review")
    public Response deleteComment(String data) {
        Gson gson = new Gson();

        ReviewDTO review = gson.fromJson(data, ReviewDTO.class);
        AdminController adminCtrl = new AdminController();

        boolean isDeleted = adminCtrl.deleteComment(review.getUserId(), review.getId());

        if (isDeleted) {
            String toJson = gson.toJson(Digester.encrypt(gson.toJson(isDeleted)));

            return successResponse(200, toJson);
        } else {
            return errorResponse(404, "Failed. Couldn't delete the chosen comment.");
        }
    }

    /**
     * Nedenstående er OPTIONS metoder udelukkende til at løse CORS-problemstillingen i forbindelse med localhost.
     * Læs mere på: https://developer.mozilla.org/en-US/docs/Web/HTTP/Access_control_CORS
     * */
    @OPTIONS
    @Path("/review")
    public Response OptionsDeleteComment() {
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .build();
    }
}
