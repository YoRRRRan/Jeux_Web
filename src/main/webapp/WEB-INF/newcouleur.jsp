<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="style.css" type="text/css" media="screen" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<meta charset="UTF-8">
<title>Couleur Bière</title>
</head>
<body>


<div class="container text-white" id="creabeer">	
<h2>Couleur</h2>
<c:if test="${not empty message}">
<p class="invalid">${message}</p>
</c:if>

  <form action="NewCouleur" method="post">
  	<div class="row align-items-center">
		<div class="col-12 col-md-3">Titre</div>
		<div class="col-12 col-md-9">
		<input type="text" class="form-control" id="nom"
			placeholder="Saisir nom" name="nom"
			value="${couleur.couleurNom}" required />
		</div>
	</div>
 	<div class="row align-items-center">
		<div class="col-12 col-md-3">Description</div>
		<div class="col-12 col-md-9">	
		<input type="text" class="form-control" id="description"
			placeholder="Saisi description" name="description" 
			value="${couleur.couleurDescription}" required />
		</div>
	</div>
	
   	<div class="row align-items-center">
		<div class="col-12 col-md-5 offset-md-3">		
			<button type="submit" class="btn btn-primary mt-3">Add/Update</button>
		</div>
		<div class="col-12 col-md-4">		
			<a class="btn btn-primary mt-3" href="ListBiere">Retour</a>
		</div>
	</div>
  </form>
</div>
</body>
</html>