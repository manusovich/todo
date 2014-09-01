package com.mashape.testtask.todo.rest.resource;

import com.mashape.testtask.todo.api.exception.TodoApplicationException;
import com.mashape.testtask.todo.api.model.TodoEntity;
import com.mashape.testtask.todo.api.model.UserRoles;
import com.mashape.testtask.todo.api.service.TodoService;
import com.mashape.testtask.todo.rest.TodoRestApplication;
import com.mashape.testtask.todo.rest.model.Todo;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Todо rest service implementation
 * <p/>
 * Created by Alex Manusovich on 8/30/14.
 */
@Component
@Path("/todo")
@PermitAll
public class TodoResource {

    @Autowired
    private TodoService todoService;

    /*
     * Create
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    @RolesAllowed(UserRoles.AUTHORIZED)
    public Response create(
            @CookieParam(value = TodoRestApplication.COOKIE_SESSION_PHONE) String phone,
            @Valid Todo todo) throws TodoApplicationException {

        getTodoService().createTodo(phone,
                todo.getTitle(), todo.getBody(), todo.isDone());

        return Response.status(Response.Status.CREATED)
                .entity("A new todo item has been created")
                .build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.TEXT_HTML})
    @Transactional
    @RolesAllowed(UserRoles.AUTHORIZED)
    public Response createFormEncoded(
            @CookieParam(value = TodoRestApplication.COOKIE_SESSION_PHONE) String phone,
            @Length(min = 5, message = "The field title has to be at least 5 characters")
            @FormParam(TodoEntity.TITLE) String title,
            @FormParam(TodoEntity.BODY) String body,
            @FormParam(TodoEntity.DONE) boolean done) throws TodoApplicationException {

        getTodoService().createTodo(phone, title, body, done);

        return Response
                .status(Response.Status.CREATED)
                .entity("A new todo item has been created")
                .build();
    }


    @POST
    @Path("list")
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed(UserRoles.AUTHORIZED)
    public Response createList(
            @CookieParam(value = TodoRestApplication.COOKIE_SESSION_PHONE) String phone,
            @Valid List<Todo> todoList) throws TodoApplicationException {

        for (Todo todo : todoList) {
            getTodoService().createTodo(phone,
                    todo.getTitle(), todo.getBody(), todo.isDone());
        }

        return Response.status(Response.Status.CREATED)
                .entity("List of todo items has been successfully created").build();
    }

    /*
     * Read
     */
    @GET
    @Produces({MediaType.TEXT_HTML})
    public Response get() throws TodoApplicationException {
        return Response.status(Response.Status.OK)
                .entity("Todo API.").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @RolesAllowed(UserRoles.AUTHORIZED)
    public List<Todo> getAll(
            @CookieParam(value = TodoRestApplication.COOKIE_SESSION_PHONE) String phone,
            @QueryParam("q") String query)
            throws TodoApplicationException {
        List<Todo> result = new ArrayList<Todo>();

        List<TodoEntity> todoEntities;
        if (StringUtils.isEmpty(query)) {
            todoEntities = getTodoService().readAll(phone);
        } else {
            todoEntities = getTodoService().search(phone, query);
        }

        if (todoEntities != null) {
            for (TodoEntity entity : todoEntities) {
                result.add(new Todo(entity));
            }
        }
        return result;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @RolesAllowed(UserRoles.AUTHORIZED)
    public Response getById(
            @CookieParam(value = TodoRestApplication.COOKIE_SESSION_PHONE) String phone,
            @PathParam("id") String id)
            throws TodoApplicationException {
        TodoEntity entity = getTodoService().readById(phone, id);
        if (entity != null) {
            Todo todo = new Todo(entity);
            return Response
                    .status(Response.Status.OK)
                    .entity(todo)
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("We don't have record with this ID")
                    .build();
        }
    }

    /*
     * Update
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_HTML})
    @RolesAllowed(UserRoles.AUTHORIZED)
    public Response putById(
            @CookieParam(value = TodoRestApplication.COOKIE_SESSION_PHONE) String phone,
            @Valid Todo todo) throws TodoApplicationException {

        TodoEntity entity = getTodoService().readById(phone, todo.getId());
        if (entity == null) {
            // resource not existent yet, and should be created
            todoService.createTodo(phone, todo.getTitle(),
                    todo.getBody(), todo.isDone());
            return Response
                    .status(Response.Status.CREATED)
                    .build();
        } else {
            /**
             * We need clean instance on this step, because we are going to make full update
             * according PUT specification
             */
            TodoEntity updatedEntity = new TodoEntity();
            /**
             * Phone is not presented in Todо object and we have to set this manually
             */
            updatedEntity.setPhone(phone);
            /**
             * Everything else can be copied as is from Todо
             */
            BeanUtils.copyProperties(todo, updatedEntity);
            todoService.update(updatedEntity);
            return Response
                    .status(Response.Status.OK)
                    .build();
        }
    }

    /*
     * Delete
     */
    @DELETE
    @Path("{id}")
    @Produces({MediaType.TEXT_HTML})
    @RolesAllowed(UserRoles.AUTHORIZED)
    public Response deleteById(
            @CookieParam(value = TodoRestApplication.COOKIE_SESSION_PHONE) String phone,
            @PathParam("id") String id) {
        todoService.remove(phone, id);
        return Response.status(Response.Status.NO_CONTENT)
                .entity("Item has been successfully removed")
                .build();
    }

    @DELETE
    @Produces({MediaType.TEXT_HTML})
    @RolesAllowed(UserRoles.AUTHORIZED)
    public Response deleteAll(
            @CookieParam(value = TodoRestApplication.COOKIE_SESSION_PHONE) String phone) {
        todoService.removeAll(phone);
        return Response.status(Response.Status.NO_CONTENT)
                .entity("All items have been successfully removed").build();
    }

    public TodoService getTodoService() {
        return todoService;
    }

    public void setTodoService(TodoService todoService) {
        this.todoService = todoService;
    }
}
