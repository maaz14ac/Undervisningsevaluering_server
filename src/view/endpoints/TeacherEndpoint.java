package view.endpoints;

import com.google.gson.Gson;
import logic.TeacherController;
import logic.UserController;
import shared.ReviewDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by Kasper on 19/10/2016.
 */
@Path("/api/teacher")
public class TeacherEndpoint extends UserEndpoint {
    /**
     * En metode til at hente lektioner for et enkelt kursus i form af en JSON String.
     *
     * @param lectureId Fagkoden på det kursus man ønsker at hente et gennemsnit på.
     * @return En double
     */
    @GET
    @Path("/lecture/stat/{lectureId}")
    public Response getAvgRatingLecture(@PathParam("lectureId") int lectureId) {
        Gson gson = new Gson();
        TeacherController teacherCtrl = new TeacherController();
        //UserController userCtrl = new UserController();
        //ArrayList<ReviewDTO> reviews = userCtrl.getReviews(lectureId);
        double lectureAvg = teacherCtrl.calculateAverageRatingOnLecture(lectureId);

        return successResponse(200, lectureAvg);
    }

    /**
     * Nedenstående er OPTIONS metoder udelukkende til at løse CORS-problemstillingen i forbindelse med localhost.
     * Læs mere på: https://developer.mozilla.org/en-US/docs/Web/HTTP/Access_control_CORS
     * */
    @OPTIONS
    @Path("/lecture/stat/{lectureId}")
    public Response optionsGetAvgRatingLecture() {
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type")
                .build();
    }
}
