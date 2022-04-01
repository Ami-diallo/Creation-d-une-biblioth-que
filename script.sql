begin transaction;
DROP SCHEMA IF EXISTS Biblio CASCADE;
create schema Biblio;
set search_path to Biblio; 


--Creation tables
\echo ' '
\echo 'Creation de tables'
\echo '----'
create table Auteur (
  no_auteur integer not null,
  nom_auteur text not null,
  primary key (no_auteur)
);

create table Livre (
  no_livre integer not null,
  titre text not null,
  genre text not null,  
  auteur integer not null,
  primary key (no_livre)
);

create table Adherant (
  no_adherant integer not null,
  nom_adherant text not null,  
  no_rue integer not null,
  rue text not null,
  ville text not null,
  cp text not null,
  primary key (no_adherant)
);

create table Commande (
  no_commande SERIAL,
  no_adherant integer not null,  
  no_livre integer not null,
  statut text,
  primary key (no_commande)
);

create table Emprunt (
  no_emprunt SERIAL,
  no_adherant integer not null,  
  no_livre integer not null,
  date_emprunt date not null,
  date_retour date,
  primary key (no_emprunt)
);


-- Cle secondaires
\echo ' '
\echo 'Creation cle secondaires'
\echo '----'
alter table Livre
add constraint auteurfk
foreign key (auteur) references Auteur(no_auteur);

alter table Commande
add constraint cmdAdhfk
foreign key (no_adherant) references Adherant(no_adherant);

alter table Commande
add constraint cmdLvrfk
foreign key (no_livre) references Livre(no_livre);

alter table Emprunt
add constraint empAdhfk
foreign key (no_adherant) references Adherant(no_adherant);

alter table Emprunt
add constraint empLvrfk
foreign key (no_livre) references Livre(no_livre);


-- Check
\echo ' '
\echo 'Creation contraintes'
\echo '----'
alter table Emprunt
   add constraint date_retour check (date_emprunt < date_retour);
   
   
--TRIGGER
\echo ' '
\echo 'Creation trigger'
\echo '----'
-- Une commande active a un statut a NULL. un adherant ne peut pas avoir plus de 3
-- commandes actives a la fois.
CREATE FUNCTION cmd_limite() RETURNS trigger AS $cmd_limite$
	declare
		total integer;
    BEGIN
	
		SELECT count(*) into total FROM Commande WHERE no_adherant = NEW.no_adherant AND statut IS NULL;
		
        --  On ne peut pas avoir plus de 3 commandes actives (statut NULL)
        IF total >= 3 AND NEW.statut IS NULL THEN
            RAISE EXCEPTION 'Un adherant ne peut pas avoir plus de 3 commandes actives.';
        END IF;

        RETURN NEW;
    END;
$cmd_limite$ LANGUAGE plpgsql;

CREATE TRIGGER cmd_limite BEFORE INSERT OR UPDATE ON Commande
    FOR EACH ROW EXECUTE PROCEDURE cmd_limite();


-- Insertion
\echo ' '
\echo 'Insertions'
\echo '----'
INSERT INTO Auteur (no_auteur, nom_auteur) VALUES (1, 'Isaac Asimov');
INSERT INTO Auteur (no_auteur, nom_auteur) VALUES (2, 'H. P. Lovecraft');
INSERT INTO Auteur (no_auteur, nom_auteur) VALUES (3, 'Terry Pratchett');
INSERT INTO Auteur (no_auteur, nom_auteur) VALUES (4, 'Frank Herbert');
INSERT INTO Auteur (no_auteur, nom_auteur) VALUES (5, 'Dan Simmons');
INSERT INTO Auteur (no_auteur, nom_auteur) VALUES (6, 'Stephen King');
INSERT INTO Auteur (no_auteur, nom_auteur) VALUES (7, 'J. K. Rowling');
INSERT INTO Auteur (no_auteur, nom_auteur) VALUES (8, 'Carl Sagan');
INSERT INTO Auteur (no_auteur, nom_auteur) VALUES (9, 'J. R. R. Tolkien');
INSERT INTO Auteur (no_auteur, nom_auteur) VALUES (10, 'Agatha Christie');

INSERT INTO Livre (no_livre, titre, genre, auteur) VALUES (1, 'Foundation', 'Science fiction', 1);
INSERT INTO Livre (no_livre, titre, genre, auteur) VALUES (2, 'I, Robot', 'Science fiction', 1);
INSERT INTO Livre (no_livre, titre, genre, auteur) VALUES (3, 'The Shadow Over Innsmouth', 'Horreur', 2);
INSERT INTO Livre (no_livre, titre, genre, auteur) VALUES (4, 'The Call of Cthulhu', 'Horreur', 2);
INSERT INTO Livre (no_livre, titre, genre, auteur) VALUES (5, 'The Colour of Magic', 'Fantaisie', 3);
INSERT INTO Livre (no_livre, titre, genre, auteur) VALUES (6, 'Wyrd Sisters', 'Fantaisie', 3);
INSERT INTO Livre (no_livre, titre, genre, auteur) VALUES (7, 'Dune', 'Science fiction', 4);
INSERT INTO Livre (no_livre, titre, genre, auteur) VALUES (8, 'Hyperion', 'Science fiction', 5);
INSERT INTO Livre (no_livre, titre, genre, auteur) VALUES (9, 'Carrie', 'Horreur', 6);
INSERT INTO Livre (no_livre, titre, genre, auteur) VALUES (10, 'Harry Potter and the Philosopher''s Stone', 'Fantaisie', 7);
INSERT INTO Livre (no_livre, titre, genre, auteur) VALUES (11, 'Cosmos', 'Science', 8);
INSERT INTO Livre (no_livre, titre, genre, auteur) VALUES (12, 'The Hobbit', 'Science', 9);
INSERT INTO Livre (no_livre, titre, genre, auteur) VALUES (13, 'And Then There Were None', 'Mystere', 10);

INSERT INTO Adherant (no_adherant, nom_adherant, no_rue, rue, ville, cp) VALUES (1, 'John Smith', 123, 'Rue du sapin', 'Montreal', 'A5AD9F');
INSERT INTO Adherant (no_adherant, nom_adherant, no_rue, rue, ville, cp) VALUES (2, 'Annie France', 4567, 'Boul. de la rose', 'Montreal', 'D7F9G6');
INSERT INTO Adherant (no_adherant, nom_adherant, no_rue, rue, ville, cp) VALUES (3, 'Dan Blais', 8, 'Rue du coin', 'Montreal', 'N7G5F4');
INSERT INTO Adherant (no_adherant, nom_adherant, no_rue, rue, ville, cp) VALUES (4, 'Sallie Dubois', 765, 'Boul. VII', 'Montreal', 'L0D6F5');
INSERT INTO Adherant (no_adherant, nom_adherant, no_rue, rue, ville, cp) VALUES (5, 'Sam Dulac', 222, 'Rue du puit', 'Montreal', 'G8D6F5');
INSERT INTO Adherant (no_adherant, nom_adherant, no_rue, rue, ville, cp) VALUES (6, 'Jim Boivin', 1, 'Chemin du village', 'Montreal', 'A4A4A4');
INSERT INTO Adherant (no_adherant, nom_adherant, no_rue, rue, ville, cp) VALUES (7, 'Nadine Noel', 3456, 'Route 23', 'Montreal', 'C8D7G6');
INSERT INTO Adherant (no_adherant, nom_adherant, no_rue, rue, ville, cp) VALUES (8, 'Sylvie Dulac', 4, 'Rue des nuages', 'Montreal', 'L0D7B5');
INSERT INTO Adherant (no_adherant, nom_adherant, no_rue, rue, ville, cp) VALUES (9, 'Jean Antoine', 50, 'Chemin du lac', 'Montreal', 'F6R4D9');
INSERT INTO Adherant (no_adherant, nom_adherant, no_rue, rue, ville, cp) VALUES (10, 'Michel Ande', 45, 'Route verte', 'Montreal', 'G6D4D1');

INSERT INTO Emprunt (no_adherant, no_livre, date_emprunt) VALUES (1, 1, '2021-03-05');
INSERT INTO Emprunt (no_adherant, no_livre, date_emprunt) VALUES (1, 2, CURRENT_DATE);
INSERT INTO Emprunt (no_adherant, no_livre, date_emprunt, date_retour) VALUES (2, 9, '2000-05-05', '2000-05-07');
INSERT INTO Emprunt (no_adherant, no_livre, date_emprunt, date_retour) VALUES (2, 4, '2000-06-05', '2000-06-07');
INSERT INTO Emprunt (no_adherant, no_livre, date_emprunt, date_retour) VALUES (2, 5, '2000-07-05', '2000-07-07');
INSERT INTO Emprunt (no_adherant, no_livre, date_emprunt, date_retour) VALUES (3, 8, '2000-05-05', '2000-05-07');
INSERT INTO Emprunt (no_adherant, no_livre, date_emprunt, date_retour) VALUES (3, 7, '2000-05-05', '2000-05-07');
INSERT INTO Emprunt (no_adherant, no_livre, date_emprunt) VALUES (1, 5, CURRENT_DATE - 15);
INSERT INTO Emprunt (no_adherant, no_livre, date_emprunt) VALUES (10, 7, CURRENT_DATE - 12);
INSERT INTO Emprunt (no_adherant, no_livre, date_emprunt) VALUES (6, 9, CURRENT_DATE - 2);
INSERT INTO Emprunt (no_adherant, no_livre, date_emprunt) VALUES (7, 1, '2021-03-21');
INSERT INTO Emprunt (no_adherant, no_livre, date_emprunt) VALUES (5, 12, '2021-03-12');

INSERT INTO Commande (no_adherant, no_livre) VALUES (2, 1);
INSERT INTO Commande (no_adherant, no_livre) VALUES (2, 3);
INSERT INTO Commande (no_adherant, no_livre) VALUES (2, 4);
INSERT INTO Commande (no_adherant, no_livre) VALUES (3, 2);
INSERT INTO Commande (no_adherant, no_livre) VALUES (3, 5);
INSERT INTO Commande (no_adherant, no_livre, statut) VALUES (3, 6, 'ANNULE');
INSERT INTO Commande (no_adherant, no_livre) VALUES (3, 11);
INSERT INTO Commande (no_adherant, no_livre, statut) VALUES (6, 8, 'HONOREE');
INSERT INTO Commande (no_adherant, no_livre, statut) VALUES (9, 9, 'HONOREE');
INSERT INTO Commande (no_adherant, no_livre, statut) VALUES (8, 13, 'HONOREE');

\echo ' '
-- Requetes

-- Tables de base
\echo 'Auteur'
\echo '----'
select * from Auteur;
\echo 'Livre'
\echo '----'
select * from Livre;
\echo 'Adherant'
\echo '----'
select * from Adherant;
\echo 'Commande'
\echo '----'
select * from Commande;
\echo 'Emprunt'
\echo '----'
select * from Emprunt;

\echo 'Question 1 : Quel est le nombre de livres de science fiction écrits pour chaque auteur présent dans la bibliothèque ?'
\echo '----'
select nom_auteur as auteur, count(no_livre) as nb_scifi from Auteur, Livre where genre = 'Science fiction' and livre.auteur = auteur.no_auteur GROUP BY no_auteur;

\echo 'Question 2 : Quels sont les adhérents vivant à Montréal ayant emprunté un livre d’horreur avant le 16 juin 2020 ?'
\echo '----'
select distinct(nom_adherant) as mtl_adherant from Adherant, Emprunt, biblio.Livre where emprunt.no_adherant = adherant.no_adherant and emprunt.no_livre = livre.no_livre and ville = 'Montreal' and genre = 'Horreur' and date_emprunt < '2020-06-16';

\echo 'Question 3 : Quels sont les noms et les dates d’emprunt de tous les adhérents qui ont des livres en retard ?'
\echo '----'
select nom_adherant as nom, date_emprunt from Adherant natural join Emprunt where date_retour is NULL AND date_emprunt + 14 < CURRENT_DATE;

\echo 'Question 4 : Quels sont les noms, adresses et nombre de livres empruntés pour chaque adhérent qui a emprunté plus que d’un livre?'
\echo '----'
with R1 as (select count(*) as nb_livre, no_adherant from emprunt group by no_adherant) SELECT distinct(nom_adherant), no_rue, rue, ville, cp, nb_livre from emprunt natural join Adherant natural join R1 where nb_livre > 1;

commit;