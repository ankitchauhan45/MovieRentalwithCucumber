package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiSteps {

    private static RequestSpecification requestSpec;
    private static ResponseSpecification responseSpec;
    private Response response;
    // Register a new user
    @Given("the user registration endpoint is available")
    public void theUserRegistrationEndpointIsAvailable() {
        RestAssured.baseURI = "http://localhost:2000";
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
    }

    @When("I register a user with username {string}, password {string}, and email {string}")
    public void iRegisterAUser(String username, String password, String email) {
        response = given()
            .spec(requestSpec)
            .body(new User(username, password, email))
            .post("/users/register");
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("the response body should contain username {string} and email {string}")
    public void theResponseBodyShouldContainUsernameAndEmail(String username, String email) {
        response.then()
            .body("username", equalTo(username))
            .body("email", equalTo(email));
    }
   
    // Login a user
    @Given("the user login endpoint is available")
    public void theUserLoginEndpointIsAvailable() {
        // Already set up in the Given method for registration
    }

    @When("I login with username {string} and password {string}")
    public void iLoginWithUsernameAndPassword(String username, String password) {
        response = given()
            .spec(requestSpec)
            .body(new User(username, password, null))
            .post("/users/login");
    }

    @Then("the response body should contain message {string}")
    public void theResponseBodyShouldContainMessage(String message) {
        response.then().body("message", equalTo(message));
    }
    
    // Update user details
    @Given("the user update endpoint is available")
    public void theUserUpdateEndpointIsAvailable() {
        // Already set up in the Given method for registration
    }

    @When("I update user with id {int} with username {string} and email {string}")
    public void iUpdateUserWithIdWithUsernameAndEmail(int userId, String username, String email) {
        response = given()
            .spec(requestSpec)
            .body(new User(username, null, email))
            .put("/users/" + userId);
    }
       
    // Delete a user
    @Given("the user delete endpoint is available")
    public void theUserDeleteEndpointIsAvailable() {
        // Already set up in the Given method for registration
    }

    @When("I delete user with id {int}")
    public void iDeleteUserWithId(int userId) {
        response = given()
            .spec(requestSpec)
            .delete("/users/" + userId);
    }
    @Then("the response status code should be {int} for deletion")
    public void theResponseStatusCodeShouldBeForDeletion(int statusCode) {
        response.then().statusCode(statusCode);
    }
   
    
    // Post a movie
    @Given("the movie post endpoint is available")
    public void theMoviePostEndpointIsAvailable() {
        // Already set up in the Given method for registration
    }

    @When("I post a movie with title {string}, director {string}, genre {string}, and releaseDate {string}")
    public void iPostAMovieWithDetails(String title, String director, String genre, String releaseDate) {
        response = given()
            .spec(requestSpec)
            .body(new Movie(title, director, genre, releaseDate))
            .post("/movies");
    }

    @Then("the response body should contain title {string} and director {string}")
    public void theResponseBodyShouldContainTitleAndDirector(String title, String director) {
        response.then()
            .body("title", equalTo(title))
            .body("director", equalTo(director));
    }
    
    // Get a movie by ID
    @Given("the movie get by ID endpoint is available")
    public void theMovieGetByIdEndpointIsAvailable() {
        // Already set up in the Given method for registration
    }

    @When("I get the movie with ID {int}")
    public void iGetTheMovieWithId(int movieId) {
        response = given()
            .spec(requestSpec)
            .get("/movies/" + movieId);
    }
    @Then("the response body should contain title {string}")
    public void theResponseBodyShouldContainTitle(String title) {
        response.then().body("title", equalTo(title));
    }
    
    // Post a rental
    @Given("the rental post endpoint is available")
    public void theRentalPostEndpointIsAvailable() {
        // This is already covered by the general setup in the @Given for other endpoints
    }

    @When("I post a rental with userId {int}, movieId {int}, and rentalDate {string}")
    public void iPostARentalWithDetails(int userId, int movieId, String rentalDate) {
        response = given()
            .spec(requestSpec)
            .body(new Rental(userId, movieId, rentalDate))
            .post("/rentals");
    }
    @Then("the response body should contain userId {int} and movieId {int}")
    public void theResponseBodyShouldContainUserIdAndMovieId(int userId, int movieId) {
        response.then()
            .body("userId", equalTo(userId))
            .body("movieId", equalTo(movieId));
    }
    
    // Get all rentals
    @Given("the rentals get all endpoint is available")
    public void theRentalsGetAllEndpointIsAvailable() {
        // Already set up in the Given method for registration
    }

    @When("I get all rentals")
    public void iGetAllRentals() {
        response = given()
            .spec(requestSpec)
            .get("/rentals");
    }
    @Then("the response body should contain rentals")
    public void theResponseBodyShouldContainRentals() {
        response.then().body("size()", greaterThan(0));
    }
}
