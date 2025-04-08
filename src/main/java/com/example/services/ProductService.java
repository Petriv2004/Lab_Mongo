/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.services;

import com.example.PersistenceManager;
import com.example.models.Product;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author RayAj
 */
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductService {

    @PersistenceContext(unitName = "mongoPU")
    EntityManager entityManager;

    @PostConstruct
    public void init() {
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts() {
        Query q = entityManager.createQuery("SELECT p FROM Product p ORDER BY p.nombre ASC");
        List<Product> products = q.getResultList();
        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .entity(products)
                .build();
    }

    @GET
    @Path("/byLetterA")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompetitorsByLetterA() {
        Query q = entityManager.createQuery("SELECT c.name FROM Competitor c WHERE c.name LIKE 'A%' ORDER BY c.name ASC");
        List<String> names = q.getResultList();
        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .entity(names)
                .build();
    }

    @OPTIONS
    public Response cors(@javax.ws.rs.core.Context HttpHeaders requestHeaders
    ) {
        return Response.status(200).header("Access-Control-Allow-Origin", "*").header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS").header("Access-Control-Allow-Headers", "AUTHORIZATION, content-type, accept").build();
    }
}
