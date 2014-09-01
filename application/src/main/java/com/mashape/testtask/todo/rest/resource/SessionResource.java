package com.mashape.testtask.todo.rest.resource;

import com.mashape.testtask.todo.api.exception.IndexingException;
import com.mashape.testtask.todo.api.model.UserRoles;
import com.mashape.testtask.todo.rest.TodoRestApplication;
import com.mashape.testtask.todo.rest.model.Session;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/**
 * Created by Alex Manusovich on 8/31/14.
 */
@Component
@Path("/session")
@PermitAll
public class SessionResource {
    /*
     * Create
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(@Valid Session session) throws IndexingException {
        return Response.status(Response.Status.CREATED)
                .cookie(createSessionCookie(session.getPhone()))
                .build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.TEXT_HTML})
    public Response createFormEncoded(
            @Pattern(regexp = "\\+[1-9]\\d+", message = "Phone number has to be with + and country code.")
            @FormParam(Session.PHONE) String phone) throws IndexingException {
        return Response
                .status(Response.Status.CREATED)
                .cookie(createSessionCookie(phone))
                .entity("Session has been created")
                .build();
    }

    private NewCookie createSessionCookie(String phone) {
        return new NewCookie(
                TodoRestApplication.COOKIE_SESSION_PHONE,
                phone,
                "/",  // path
                null, //domain
                null, // comment
                NewCookie.DEFAULT_MAX_AGE,
                false);
    }

    /*
     * Read
     */
    @GET
    @Produces({MediaType.TEXT_HTML})
    public Response get(
            @CookieParam(value = TodoRestApplication.COOKIE_SESSION_PHONE) String phone) {
        String currentSession = "";
        if (!StringUtils.isEmpty(phone)) {
            currentSession = " Your current session token is " + phone;
        }
        return Response.status(Response.Status.OK)
                .entity("Session API." + currentSession).build();
    }

    /*
     * Update
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    @RolesAllowed(UserRoles.AUTHORIZED)
    public Response putSession(
            @CookieParam(value = TodoRestApplication.COOKIE_SESSION_PHONE) String phone,
            @Valid Session newSession) {
        if (StringUtils.isEmpty(phone)) {
            return Response
                    .status(Response.Status.CREATED)
                    .cookie(createSessionCookie(newSession.getPhone()))
                    .entity("Session has been created")
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .cookie(createSessionCookie(newSession.getPhone()))
                    .entity("Session has been updated")
                    .build();
        }
    }

    /*
     * Delete
     */
    @DELETE
    @Produces({MediaType.TEXT_HTML})
    @RolesAllowed(UserRoles.AUTHORIZED)
    public Response deleteSession() {
        return Response
                .status(Response.Status.NO_CONTENT)
                .cookie(createSessionCookie(""))
                .entity("Session has been successfully destroyed").build();
    }
}
