<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
<title>Liste Bières</title>
</head>
<body>

<div class="container text-dark" id="container">	
	<h1 class="titre">Liste des bières</h1>
	<div class="row">			
				
		<form action="ListBiere" method="get" class="box">
			<label for="couleur">Choisir une couleur : </label>
			<select name="couleur" id="couleur">
				<option value="all">Toutes les couleurs</option>
				<c:forEach var="couleur" items="${couleur}">
					<option value="${couleur.couleurId}"
					<c:if test="${not empty selected and couleur.couleurId == selected}">
						selected
					</c:if>
					>${couleur.couleurNom}</option>			
				</c:forEach>
				<option value="new">Ajouter une couleur</option>
			</select> 
			
			<label for="fermentation">Choisir une fermentation : </label>
			<select name="fermentation" id="fermentation">
				<option value="all">Toutes les fermentations</option>
				<c:forEach var="fermentation" items="${fermentation}">
				<option value="${fermentation.fermentationId}"
					<c:if test="${not empty selectedFermentation and fermentation.fermentationId == selectedFermentation}">
					selected
					</c:if>
					>${fermentation.fermentationNom}</option>			
				</c:forEach>
				<option value="new">Ajouter une fermentation</option>
			</select> 			
		
			<input type="submit" value="Envoyer">
		</form>
					
	</div>
	<div class="row">
		<a href="BierePage" class="btn btn-primary mt-3">Create new bière</a>
	</div>
	<div class="row">
		<table>
			<thead>
				<tr>
					<th>Bières</th>
					<th>Description</th>
					<th>Couleur</th>
					<th>Fermentation</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="biere" items="${biere}">
					<tr>
						<td><a class="colorlist" href="BierePage?biereId=${biere.biereId}">${biere.biereNom}</a></td>
						<td class="colorlist">${biere.biereDescription}</td>
						<td><a class="colorlist" href="NewCouleur?couleurId=${biere.couleurId}">${biere.couleurNom}</a></td>
						<td><a class="colorlist" href="NewFermentation?fermentationId=${biere.fermentationId}">${biere.fermentationNom}</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
</body>
</html>