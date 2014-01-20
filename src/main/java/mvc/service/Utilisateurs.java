package mvc.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import mvc.model.Dao;
import mvc.model.Groupe;
import mvc.model.Message;
import mvc.model.Utilisateur;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author zak ,tak, yah, agh
 *
 */
@Path("/utilisateurs")
public class Utilisateurs {
          //http://localhost:8080/twitter_Like_server_jee/resources/utilisateurs/
          //http://localhost:8080/twitter_Like_server_jee/resources/utilisateurs/create
          //http://localhost:8080/twitter_Like_server_jee/resources/utilisateurs/{idUtil}/utilisateurASuivre
	  //http://localhost:8080/twitter_Like_server_jee/resources/utilisateurs/{idUtil}
           
          //  // pseudo=llllldll&mail=jjjdjj@hot.com&mdp=jjjjjjkkkkkk&nom=kkkkkkkkkk&pnom=hjklm
         //> curl -v --request POST --data '{"pseudo":"llllldll","mail":"jjjdjj@hot.com","mdp":"jjjdjj","nom":"moi","pnom":"hjklm"}'
         //--header "Content-Type: application/json" http://localhost:8080/twitter_Like_server_jee/resources/utilisateurs/jsonCreate/ 

     //curl -H "Accept: application/json" http://localhost:9000/FMIN362-Tweeter/resources/tweets/get


//curl -X POST --data "u=mikasa&c=good day&pdate=25/11/13&ploc=Wall Rose&tags=snk,aot" http://localhost:9000/FMIN362-Tweeter/resources/tweets/post

//  curl -X POST -H "Content-Type: application/json" -d '{"pseudo":"llllldll","mail":"jjjdjj@hot.com","mdp":"jjjdjj","nom":"moi","pnom":"hjklm"}'  'http://localhost:8080/twitter_Like_server_jee/resources/utilisateurs/jsonCreate/' 
  
    //curl --data pseudo=llllldll&mail=jjjdjj@hot.com&mdp=jjjjjjkkkkkk&nom=kkkkkkkkkk&pnom=hjklm
    
        @POST
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createUtilisateur(MultivaluedMap<String, String> params)
			throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			Utilisateur user = new Utilisateur();
			user.setNom(params.getFirst("nom"));
			user.setPnom(params.getFirst("pnom"));
			user.setPseudo(params.getFirst("pseudo"));
			user.setMail(params.getFirst("mail"));
			user.setMdp(params.getFirst("mdp"));
			user.setDateInscription(new Date());
			dao.createUser(user);
			utx.commit();

			// Renvoi des données
			return Response.ok(user, MediaType.APPLICATION_JSON).build();

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème de persistence dans la base");
			}
			throw new Exception(ex);
		}
	}
        
        
        // curl -X POST -H "Content-Type: application/json;charset=utf-8" -d '{"nom":"hello","pnom":"tarik","pseudo":"takos","mail":"tarik@gmail.com","mdp":"tarik"}' http://localhost:8080/twitter_Like_server_jee/resources/utilisateurs/jsonCreate/

	@POST
	@Path("jsonCreate")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response jsonCreateUtilisateur(String jsonUser) throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			Utilisateur user = new Utilisateur();
			ObjectMapper mapper = new ObjectMapper();
			try {
				user = mapper.readValue(jsonUser, Utilisateur.class);
			} catch (JsonParseException e1) {
				e1.printStackTrace();
			} catch (JsonMappingException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			dao.createUser(user);
			utx.commit();

			// Renvoi des données
			return Response.ok(user, MediaType.APPLICATION_JSON).build();

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème de persistence dans la base");
			}
			throw new Exception(ex);
		}
	}

        //curl -X POST -H "content-type: application/x-www-form-urlencoded" -d "name=tom" http://localhost:8080/sampleapp/hello
//curl -X POST -H "content-type: application/x-www-form-urlencoded" -d "mail=,mdp="  http://localhost:8080/twitter_Like_server_jee/resources/utilisateurs/authentification/
        //pseudo=llllldll&mail=jjjdjj@hot.com&mdp=jjjjjjkkkkkk&nom=kkkkkkkkkk&pnom=hjklm
	@POST
	@Path("authentification")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response authentificationUtilisateur(
			MultivaluedMap<String, String> params) throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			Utilisateur user = new Utilisateur();
			user = dao.getUserByEmail(params.getFirst("mail"));
			utx.commit();

			// Renvoi des données
			if (user != null) {
				if (params.getFirst("mdp").equals(user.getMdp())) {
					return Response.ok(user, MediaType.APPLICATION_JSON)
							.build();
				} else {
					return Response.status(Status.BAD_REQUEST).build();
				}
			}else{
				return Response.status(Status.NOT_FOUND).build();
			}

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème d'authentification");
			}
			throw new Exception(ex);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Utilisateur> getAllUtilisateurs() throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			List<Utilisateur> users = dao.getAllUsers();
			utx.commit();

			// Renvoi des données
			return users;

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème de recherche de tous les utilisateurs!!");
			}
			throw new Exception(ex);
		}

	}
        
         //http://localhost:8080/twitter_Like_server_jee/resources/utilisateurs/3/utilisateurASuivre
	@GET
	@Path("{idUtil}/utilisateurASuivre")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Utilisateur> getAllUtilisateursASuivre(
			@PathParam("idUtil") long idUtil) throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			List<Utilisateur> users = dao.getAllUsersToFollow(idUtil);
			utx.commit();

			// Renvoi des données
			return users;

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème de recherche de tous les utilisateurs à suivre!!");
			}
			throw new Exception(ex);
		}

	}
        
        //http://localhost:8080/twitter_Like_server_jee/resources/utilisateurs/3/
	@GET
	@Path("{idUtil}")
	@Produces(MediaType.APPLICATION_JSON)
	public Utilisateur getUtilisateur(@PathParam("idUtil") long id)
			throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			Utilisateur user = dao.getUserById(id);
			utx.commit();

			// Renvoi des données
			return user;

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème de recherche d'utilisateur!!");
			}
			throw new Exception(ex);
		}

	}

        
        //http://localhost:8080/twitter_Like_server_jee/resources/utilisateurs/groupe/1
	@GET
	@Path("groupe/{idgrp}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Utilisateur> getAllUtilisateursOfGroupe(
			@PathParam("idgrp") long idgrp) throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			List<Utilisateur> members = dao.getAllMembersByGrpId(idgrp);
			utx.commit();

			// Renvoi des données
			return members;

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème de recherche de tous les utilisateurs d'un groupe!!");
			}
			throw new Exception(ex);
		}

	}
        //regoidre un groupe !!!
	@PUT
	@Path("{idUtil}/rejoindre/{idGrpe}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.WILDCARD)
	public Response rejoindreGroupe(@PathParam("idUtil") long idUtil,
			@PathParam("idGrpe") long idGrpe) throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			Groupe grp = dao.getGrpById(idGrpe);
			Utilisateur user = dao.getUserById(idUtil);
			user.getGrpsAbonnement().add(grp);
			grp.getMembers().add(user);
			dao.updateGrp(grp);
			utx.commit();

			// Renvoi des données
			return Response.ok(user, MediaType.APPLICATION_JSON).build();

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème de persistence dans la base");
			}
			throw new Exception(ex);
		}

	}
        
        //quitter groupe
	@PUT
	@Path("{idUtil}/quitter/{idGrpe}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.WILDCARD)
	public Response quitterGroupe(@PathParam("idUtil") long idUtil,
			@PathParam("idGrpe") long idGrpe) throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			Groupe grp = dao.getGrpById(idGrpe);
			Utilisateur user = dao.getUserById(idUtil);
			user.getGrpsAbonnement().remove(grp);
			grp.getMembers().remove(user);
			dao.updateGrp(grp);
			utx.commit();

			// Renvoi des données
			return Response.ok(user, MediaType.APPLICATION_JSON).build();

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème de persistence dans la base");
			}
			throw new Exception(ex);
		}
	}
        
        //suivre un utilisateur
	@PUT
	@Path("{idUtil}/suivre/{idUtilAsuivre}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.WILDCARD)
	public Response suivreUtilisateur(@PathParam("idUtil") long idUtil,
			@PathParam("idUtilAsuivre") long idUtilAsuivre) throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			Utilisateur user = dao.getUserById(idUtil);
			Utilisateur userToFollow = dao.getUserById(idUtilAsuivre);
			user.getUserToFollow().add(userToFollow);
			dao.updateUser(user);
			utx.commit();

			// Renvoi des données
			return Response.ok(user, MediaType.APPLICATION_JSON).build();

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème de persistence dans la base");
			}
			throw new Exception(ex);
		}

	}

	@PUT
	@Path("{idUtil}/nePlusSuivre/{idUtilAsuivre}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.WILDCARD)
	public Response nePlusSuivreUtilisateur(@PathParam("idUtil") long idUtil,
			@PathParam("idUtilAsuivre") long idUtilAsuivre) throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			Utilisateur user = dao.getUserById(idUtil);
			Utilisateur userToFollow = dao.getUserById(idUtilAsuivre);
			user.getUserToFollow().remove(userToFollow);
			dao.updateUser(user);
			utx.commit();

			// Renvoi des données
			return Response.ok(user, MediaType.APPLICATION_JSON).build();

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème de persistence dans la base");
			}
			throw new Exception(ex);
		}
	}

	@PUT
	@Path("update/{idUtil}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateUtilisateur(@PathParam("idUtil") long id,
			MultivaluedMap<String, String> params) throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			Utilisateur user = dao.getUserById(id);
			if (params.getFirst("nom") != null)
				user.setNom(params.getFirst("nom"));
			if (params.getFirst("pnom") != null)
				user.setPnom(params.getFirst("pnom"));
			if (params.getFirst("pseudo") != null)
				user.setPseudo(params.getFirst("pseudo"));
			if (params.getFirst("mail") != null)
				user.setMail(params.getFirst("mail"));
			if (params.getFirst("mdp") != null)
				user.setMdp(params.getFirst("mdp"));
			dao.updateUser(user);
			utx.commit();

			// Renvoi des données
			return Response.ok(user, MediaType.APPLICATION_JSON).build();

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {
				System.out.println("problème de persistence dans la base");
			}
			throw new Exception(ex);
		}

	}

        
        //supprimmer un USER
	@DELETE
	@Path("delete/{idUtil}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.WILDCARD,MediaType.APPLICATION_JSON})
	public Response deleteUtilisateur(@PathParam("idUtil") long id)
			throws Exception {
		UserTransaction utx = null;
		Dao dao = new Dao();
		try {

			// Lookup
			InitialContext ic = new InitialContext();
			utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
			EntityManager em = (EntityManager) ic
					.lookup("java:comp/env/persistence/em");
			dao.setEm(em);

			// Transaciton
			utx.begin();
			List<Message> msgs = dao.getAllMesagesByUserId(id);
			for (Message m:msgs){
				dao.deleteMesage(m);
			}
			List<Groupe> grps = dao.getAllGrpsByAdminId(id);
			for (Groupe g:grps){
				dao.deleteGrp(g);
			}
			List<Utilisateur> users = dao.getAllUsers();
			for (Utilisateur u:users){
				if(u.getUserToFollow().contains(dao.getUserById(id))){
					u.getUserToFollow().remove(dao.getUserById(id));
				}
			}
			
			dao.deleteUserById(id);
			utx.commit();

			// Renvoi des données
			return Response.ok().build();

		} catch (Exception ex) {

			try {
				if (utx != null) {
					utx.setRollbackOnly();
				}
			} catch (Exception rollbackEx) {

			}
			throw new Exception(ex);
		}

	}

}
