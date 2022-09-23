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
<title>Page Bières</title>
</head>
<body>


	<div class="container text-white" id="creabeer">
		<h2 class="titre">Créer/Modifier un produit</h2>
		<c:if test="${not empty message}">
			<p class="invalid">${message}</p>
		</c:if>

		<form action="BierePage" method="post">
			<div class="row align-items-center">
				<div class="col-12 col-md-4">Nom : </div>
				<div class="col-12 col-md-8">
					<input type="text" class="form-control" id="nom"
						placeholder="Saisir un nom" name="nom" value="${biere.biereNom}" required />
				</div>
			</div>
			<div class="row align-items-center">
				<div class="col-12 col-md-4">Description : </div>
				<div class="col-12 col-md-8">
					<input type="text" class="form-control" id="description"
						placeholder="Saisir une description" name="description"
						value="${biere.biereDescription}" required />
				</div>
			</div>
			<div class="row align-items-center">
				<div class="col-12 col-md-4">Prix : </div>
				<div class="col-12 col-md-8">
					<input type="text" id="prix" name="prix" placeholder="Saisir un prix"
						class="form-control validate" value="${biere.prix}" required />
				</div>
			</div>

			<div class="row align-items-center">
				<div class="col-12 col-md-4">Mise en marché : </div>
				<div class="col-12 col-md-8">
					<input type="text" id="dateCommerce" name="dateCommerce"
						placeholder="date en format : AAAA-MM-JJ" class="form-control validate"
						value="${biere.dateCommerce}" required />
				</div>
			</div>
			<div class="row align-items-center">
				<div class="col-12 col-md-4">Pays : </div>
				<div class="col-12 col-md-8">
					<input type="text" id="pays" name="pays" placeholder="Saisir un pays"
						class="form-control validate" value="${biere.pays}" required />
				</div>
			</div>

			<div class="row align-items-center">
				<div class="col-12 col-md-4">Degré d'alcool : </div>
				<div class="col-12 col-md-8">
					<input type="text" id="degree" name="degree"
						placeholder="Saisir un degré d'alcool" class="form-control validate"
						value="${biere.degree}" required />
				</div>
			</div>
			<div class="row align-items-center">
				<div class="col-12 col-md-4">Goût : </div>
				<div class="col-12 col-md-8">
					<input type="text" id="gout" name="gout" placeholder="Saisir un goût"
						class="form-control validate" value="${biere.gout}" required />
				</div>
			</div>

			<div class="row align-items-center">
				<div class="col-12 col-md-4">Couleur : </div>
				<div class="col-12 col-md-8">
					<select name="couleurs" id="couleurs">
						<c:forEach var="couleur" items="${couleurs}">
							<option value="${couleur.couleurId}"
								<c:if test="${couleur.couleurId == biere.couleurId}">
					selected
				</c:if>>${couleur.couleurNom}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="row align-items-center">
				<div class="col-12 col-md-4">Fermentation : </div>
				<div class="col-12 col-md-8">
					<fieldset>
						<c:forEach var="fermentation" items="${fermentations}">
							<input type="checkbox" id="scales${fermentation.fermentationId}"
								name="fermentations" value="${fermentation.fermentationId}"
								<c:if test="${fermentation.checked}">
					checked
				</c:if>>
							<label for="scales${fermentation.fermentationId}">${fermentation.fermentationNom}</label>
						</c:forEach>
					</fieldset>

				</div>
			</div>

			<div class="row align-items-center">
				<div class="col-12 col-md-4 offset-md-3">
					<button type="submit" class="btn btn-primary mt-3">Modifier</button>
				</div>
				<div class="col-12 col-md-5">
					<a class="btn btn-primary mt-3" href="ListBiere">Retour</a>
				</div>
			</div>


		</form>
	</div>


</body>
</html>