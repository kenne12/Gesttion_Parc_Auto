--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.5
-- Dumped by pg_dump version 9.3.5
-- Started on 2015-11-26 12:46:47

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 203 (class 3079 OID 11750)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2341 (class 0 OID 0)
-- Dependencies: 203
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 170 (class 1259 OID 116094)
-- Name: adresse; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE adresse (
    id_adresse integer DEFAULT 0 NOT NULL,
    tel character varying(100),
    email character varying(20),
    bp character varying(255),
    fax character varying(255),
    siteweb character varying(100)
);


--
-- TOC entry 171 (class 1259 OID 116101)
-- Name: annee; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE annee (
    id_annee integer NOT NULL,
    nom character varying(25)
);


--
-- TOC entry 172 (class 1259 OID 116104)
-- Name: arrondissement; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE arrondissement (
    id_arrondissement integer NOT NULL,
    id_departement integer,
    nom character varying(50)
);


--
-- TOC entry 173 (class 1259 OID 116107)
-- Name: attribution; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE attribution (
    id_attribution integer NOT NULL,
    id_vehicule integer DEFAULT 0,
    id_personnel integer DEFAULT 0,
    dateattribution date,
    daterestitution date,
    datesyst date DEFAULT now()
);


--
-- TOC entry 174 (class 1259 OID 116113)
-- Name: categorievehicule; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE categorievehicule (
    id_categorievehicule integer DEFAULT 0 NOT NULL,
    nom character varying(255)
);


--
-- TOC entry 175 (class 1259 OID 116117)
-- Name: demande; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE demande (
    id_demande integer DEFAULT 0 NOT NULL,
    id_categorievehicule integer DEFAULT 0,
    id_modele integer DEFAULT 0,
    id_structure integer DEFAULT 0,
    intitule character varying(255),
    anneedemande date,
    qtedemande integer DEFAULT 0,
    anneeaccord character varying(255),
    qteaccord integer DEFAULT 0,
    validation smallint DEFAULT 0
);


--
-- TOC entry 176 (class 1259 OID 116130)
-- Name: departement; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE departement (
    id_departement integer NOT NULL,
    id_region integer,
    nom character varying(30)
);


--
-- TOC entry 177 (class 1259 OID 116133)
-- Name: district; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE district (
    id_district integer NOT NULL,
    nom character varying(255)
);


--
-- TOC entry 178 (class 1259 OID 116136)
-- Name: fonction; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE fonction (
    id_fonction integer NOT NULL,
    nom character varying(255) DEFAULT NULL::character varying
);


--
-- TOC entry 179 (class 1259 OID 116140)
-- Name: garage; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE garage (
    id_garage integer DEFAULT 0 NOT NULL,
    id_adresse integer DEFAULT 0,
    nom character varying(255) DEFAULT NULL::character varying
);


--
-- TOC entry 180 (class 1259 OID 116146)
-- Name: marque; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE marque (
    id_marque integer DEFAULT 0 NOT NULL,
    nom character varying(255) DEFAULT NULL::character varying
);


--
-- TOC entry 181 (class 1259 OID 116151)
-- Name: modeacquisition; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE modeacquisition (
    id_modeacquisition integer DEFAULT 0 NOT NULL,
    nom character varying(255)
);


--
-- TOC entry 182 (class 1259 OID 116155)
-- Name: modele; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE modele (
    id_modele integer DEFAULT 0 NOT NULL,
    id_marque integer DEFAULT 0,
    nom character varying(255) DEFAULT NULL::character varying
);


--
-- TOC entry 183 (class 1259 OID 116161)
-- Name: mois; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE mois (
    id_mois integer NOT NULL,
    nom character varying(25)
);


--
-- TOC entry 184 (class 1259 OID 116164)
-- Name: norme; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE norme (
    id_norme integer DEFAULT 0 NOT NULL,
    id_categorievehicule integer DEFAULT 0,
    id_typestructure integer DEFAULT 0,
    minimum character varying(255) DEFAULT NULL::character varying,
    maximum character varying(255) DEFAULT NULL::character varying
);


--
-- TOC entry 185 (class 1259 OID 116175)
-- Name: pays; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE pays (
    id_pays integer NOT NULL,
    nom character varying(255)
);


--
-- TOC entry 186 (class 1259 OID 116178)
-- Name: personnel; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE personnel (
    id_personnel integer DEFAULT 0 NOT NULL,
    id_fonction integer,
    id_adresse integer DEFAULT 0,
    id_service integer DEFAULT 0,
    id_structure integer DEFAULT 0,
    nom_prenom character varying(100),
    matricule character varying(20),
    cni character varying(255),
    sexe character varying(255),
    date_naissance date,
    lieu_naissance character varying(100),
    nationalite character varying(20),
    date_recrutement date,
    photo character varying(50)
);


--
-- TOC entry 187 (class 1259 OID 116188)
-- Name: reforme; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE reforme (
    id_reforme integer NOT NULL,
    id_vehicule integer DEFAULT 0,
    datemisereforme date,
    datevente date,
    montantvente numeric,
    vendu boolean
);


--
-- TOC entry 188 (class 1259 OID 116195)
-- Name: region; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE region (
    id_region integer NOT NULL,
    id_pays integer,
    nom character varying(30)
);


--
-- TOC entry 189 (class 1259 OID 116198)
-- Name: reparation; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE reparation (
    id_reparation integer DEFAULT 0 NOT NULL,
    id_typereparation integer,
    id_garage integer DEFAULT 0,
    id_vehicule integer DEFAULT 0,
    datereparation date,
    coutreparation integer DEFAULT 0,
    immobilisation character varying(255),
    datedebut date,
    datefin date,
    commentaire character varying(255)
);


--
-- TOC entry 190 (class 1259 OID 116208)
-- Name: restitution; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE restitution (
    id_restitution integer NOT NULL,
    id_personnel integer DEFAULT 0,
    id_vehicule integer DEFAULT 0,
    id_attribution integer,
    date_prise_service date,
    date_restitution date,
    idnvelleatribution integer
);


--
-- TOC entry 191 (class 1259 OID 116213)
-- Name: roleutilisateur; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE roleutilisateur (
    idroleutilisateur integer NOT NULL,
    idutilisateur integer,
    role character varying(254),
    etat character varying(254)
);


--
-- TOC entry 192 (class 1259 OID 116219)
-- Name: service; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE service (
    id_service integer DEFAULT 0 NOT NULL,
    nom character varying(255) DEFAULT NULL::character varying
);


--
-- TOC entry 193 (class 1259 OID 116224)
-- Name: sinistre; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE sinistre (
    id_sinistre integer DEFAULT 0 NOT NULL,
    id_typesinistre integer,
    id_garage integer DEFAULT 0,
    id_vehicule integer DEFAULT 0,
    datesinistre date,
    lieusinistre character varying(255),
    montant integer DEFAULT 0,
    immobilisation character varying(255),
    datedebut date,
    datefin date,
    commentaire character varying(255),
    photo character varying(255)
);


--
-- TOC entry 194 (class 1259 OID 116234)
-- Name: sourceenergie; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE sourceenergie (
    id_sourceenergie integer DEFAULT 0 NOT NULL,
    nom character varying(255) DEFAULT NULL::character varying
);


--
-- TOC entry 195 (class 1259 OID 116239)
-- Name: sourcefinancement; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE sourcefinancement (
    id_sourcefinancement integer DEFAULT 0 NOT NULL,
    nom character varying(255)
);


--
-- TOC entry 196 (class 1259 OID 116243)
-- Name: statutstructure; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE statutstructure (
    id_statutstructure smallint DEFAULT 0 NOT NULL,
    nom character varying(255)
);


--
-- TOC entry 197 (class 1259 OID 116247)
-- Name: structure; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE structure (
    id_structure integer DEFAULT 0 NOT NULL,
    id_statutstructure smallint DEFAULT 0,
    id_adresse integer DEFAULT 0,
    id_typestructure integer DEFAULT 0,
    id_arrondissement integer,
    id_district integer,
    id_region integer,
    nom character varying(255),
    localite character varying(70),
    active boolean,
    gps_nord double precision,
    gps_sud double precision
);


--
-- TOC entry 198 (class 1259 OID 116254)
-- Name: typereparation; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE typereparation (
    id_typereparation integer NOT NULL,
    nom character varying(255)
);


--
-- TOC entry 199 (class 1259 OID 116257)
-- Name: typesinistre; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE typesinistre (
    id_typesinistre integer NOT NULL,
    nom character varying(255) DEFAULT NULL::character varying
);


--
-- TOC entry 200 (class 1259 OID 116261)
-- Name: typestructure; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE typestructure (
    id_typestructure integer DEFAULT 0 NOT NULL,
    code_fr character varying(30),
    nom_fr character varying(255)
);


--
-- TOC entry 201 (class 1259 OID 116265)
-- Name: utilisateur; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE utilisateur (
    idutilisateur integer NOT NULL,
    nom character varying(255),
    prenom character varying(255),
    sexe character varying(255),
    login character varying(255),
    mdp character varying(255)
);


--
-- TOC entry 202 (class 1259 OID 116271)
-- Name: vehicule; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE vehicule (
    id_vehicule integer DEFAULT 0 NOT NULL,
    id_sourcefinancement integer DEFAULT 0,
    id_modele integer DEFAULT 0,
    id_modeacquisition integer DEFAULT 0,
    id_sourceenergie integer DEFAULT 0,
    id_categorievehicule integer DEFAULT 0,
    immatriculation character varying(255) DEFAULT NULL::character varying,
    numchassis character varying(255),
    cartegrise character varying(255) DEFAULT NULL::character varying,
    datemiseenservice date,
    nbredeplace integer DEFAULT 0,
    dateacquisition date,
    numserie character varying(255),
    puissance character varying(255),
    coutachatttc integer,
    description character varying(255),
    document character varying(255),
    nbresinistre integer,
    nbrereparation integer,
    nbrerevision integer,
    photo character varying(255),
    attribution smallint DEFAULT 0,
    restitution smallint DEFAULT 0,
    etat character varying(255)
);


--
-- TOC entry 2302 (class 0 OID 116094)
-- Dependencies: 170
-- Data for Name: adresse; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO adresse (id_adresse, tel, email, bp, fax, siteweb) VALUES (1, '679300501', 'garagelaper@yahoo.fr', '12478', '2424587125', 'www.garper.cm');
INSERT INTO adresse (id_adresse, tel, email, bp, fax, siteweb) VALUES (2, '679300501', 'garagelaper@yahoo.fr', '12478', '2424587125', 'www.garper.cm');
INSERT INTO adresse (id_adresse, tel, email, bp, fax, siteweb) VALUES (3, '679200145', 'tb@yahoo.fr', '0124', '242124789', 'www.bomba.com');
INSERT INTO adresse (id_adresse, tel, email, bp, fax, siteweb) VALUES (4, '325648', 'abc@gmail.com', '188', '3256', 'www.google.com');
INSERT INTO adresse (id_adresse, tel, email, bp, fax, siteweb) VALUES (6, '237452169', 'bic@gmail.com', '254', '32054687', 'www.yahoo.com');
INSERT INTO adresse (id_adresse, tel, email, bp, fax, siteweb) VALUES (7, '22225485', 'hdb@gmail.com', '144 Bafoussam', '22235485', 'www.hdb.org');
INSERT INTO adresse (id_adresse, tel, email, bp, fax, siteweb) VALUES (5, '201147', 'bac@gmail.com', '237', '5231', 'www.yahoo.com');
INSERT INTO adresse (id_adresse, tel, email, bp, fax, siteweb) VALUES (8, '45782545', 'garantie@yahoo.fr', '144', '2154365', 'www.lagarantie.com');


--
-- TOC entry 2303 (class 0 OID 116101)
-- Dependencies: 171
-- Data for Name: annee; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2304 (class 0 OID 116104)
-- Dependencies: 172
-- Data for Name: arrondissement; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO arrondissement (id_arrondissement, id_departement, nom) VALUES (1, 1, 'Koutaba');
INSERT INTO arrondissement (id_arrondissement, id_departement, nom) VALUES (2, 2, 'Bafoussam');
INSERT INTO arrondissement (id_arrondissement, id_departement, nom) VALUES (3, 3, 'Dschang');


--
-- TOC entry 2305 (class 0 OID 116107)
-- Dependencies: 173
-- Data for Name: attribution; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO attribution (id_attribution, id_vehicule, id_personnel, dateattribution, daterestitution, datesyst) VALUES (3, 3, 3, '2015-10-06', NULL, '2015-10-06');
INSERT INTO attribution (id_attribution, id_vehicule, id_personnel, dateattribution, daterestitution, datesyst) VALUES (1, 2, 2, '2015-10-01', NULL, '2015-10-01');
INSERT INTO attribution (id_attribution, id_vehicule, id_personnel, dateattribution, daterestitution, datesyst) VALUES (2, 1, 1, '2015-10-01', '2015-10-12', '2015-10-01');
INSERT INTO attribution (id_attribution, id_vehicule, id_personnel, dateattribution, daterestitution, datesyst) VALUES (4, 1, 4, '2015-10-12', NULL, '2015-10-12');


--
-- TOC entry 2306 (class 0 OID 116113)
-- Dependencies: 174
-- Data for Name: categorievehicule; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO categorievehicule (id_categorievehicule, nom) VALUES (1, 'Camion');
INSERT INTO categorievehicule (id_categorievehicule, nom) VALUES (2, 'Pick UP');
INSERT INTO categorievehicule (id_categorievehicule, nom) VALUES (3, 'Gros Porteur');
INSERT INTO categorievehicule (id_categorievehicule, nom) VALUES (4, '18 Roues');


--
-- TOC entry 2307 (class 0 OID 116117)
-- Dependencies: 175
-- Data for Name: demande; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO demande (id_demande, id_categorievehicule, id_modele, id_structure, intitule, anneedemande, qtedemande, anneeaccord, qteaccord, validation) VALUES (1, 3, 1, 1, 'Besoin D''Ambulance', '2015-10-03', 5, '', NULL, NULL);


--
-- TOC entry 2308 (class 0 OID 116130)
-- Dependencies: 176
-- Data for Name: departement; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO departement (id_departement, id_region, nom) VALUES (1, 1, 'Noun');
INSERT INTO departement (id_departement, id_region, nom) VALUES (2, 1, 'Mifi');
INSERT INTO departement (id_departement, id_region, nom) VALUES (3, 1, 'Menoua');
INSERT INTO departement (id_departement, id_region, nom) VALUES (4, 1, 'Nde');
INSERT INTO departement (id_departement, id_region, nom) VALUES (5, 2, 'Bam Inoubou');


--
-- TOC entry 2309 (class 0 OID 116133)
-- Dependencies: 177
-- Data for Name: district; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO district (id_district, nom) VALUES (1, 'Foumban');
INSERT INTO district (id_district, nom) VALUES (2, 'Bafoussam');
INSERT INTO district (id_district, nom) VALUES (3, 'Bagante');
INSERT INTO district (id_district, nom) VALUES (4, 'Douala');


--
-- TOC entry 2310 (class 0 OID 116136)
-- Dependencies: 178
-- Data for Name: fonction; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO fonction (id_fonction, nom) VALUES (1, 'Directeur');
INSERT INTO fonction (id_fonction, nom) VALUES (2, 'Comptable');
INSERT INTO fonction (id_fonction, nom) VALUES (3, 'Medecin');


--
-- TOC entry 2311 (class 0 OID 116140)
-- Dependencies: 179
-- Data for Name: garage; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO garage (id_garage, id_adresse, nom) VALUES (1, 1, 'Garage la Performance');
INSERT INTO garage (id_garage, id_adresse, nom) VALUES (2, 2, 'Garage letoie');
INSERT INTO garage (id_garage, id_adresse, nom) VALUES (3, 8, 'Garage La garantie');


--
-- TOC entry 2312 (class 0 OID 116146)
-- Dependencies: 180
-- Data for Name: marque; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO marque (id_marque, nom) VALUES (1, 'Toyota');
INSERT INTO marque (id_marque, nom) VALUES (2, 'Hilux');
INSERT INTO marque (id_marque, nom) VALUES (3, 'Mercedes');
INSERT INTO marque (id_marque, nom) VALUES (4, 'Renault');


--
-- TOC entry 2313 (class 0 OID 116151)
-- Dependencies: 181
-- Data for Name: modeacquisition; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO modeacquisition (id_modeacquisition, nom) VALUES (1, 'Don');
INSERT INTO modeacquisition (id_modeacquisition, nom) VALUES (2, 'Pret');


--
-- TOC entry 2314 (class 0 OID 116155)
-- Dependencies: 182
-- Data for Name: modele; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO modele (id_modele, id_marque, nom) VALUES (1, 1, 'Advensis');
INSERT INTO modele (id_modele, id_marque, nom) VALUES (2, 1, 'Double Cabine');


--
-- TOC entry 2315 (class 0 OID 116161)
-- Dependencies: 183
-- Data for Name: mois; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2316 (class 0 OID 116164)
-- Dependencies: 184
-- Data for Name: norme; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO norme (id_norme, id_categorievehicule, id_typestructure, minimum, maximum) VALUES (1, NULL, NULL, '1000000', '6000000');
INSERT INTO norme (id_norme, id_categorievehicule, id_typestructure, minimum, maximum) VALUES (2, NULL, NULL, '500000', '2000000');


--
-- TOC entry 2317 (class 0 OID 116175)
-- Dependencies: 185
-- Data for Name: pays; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO pays (id_pays, nom) VALUES (1, 'Cameroun');
INSERT INTO pays (id_pays, nom) VALUES (2, 'France');
INSERT INTO pays (id_pays, nom) VALUES (3, 'Ghana');


--
-- TOC entry 2318 (class 0 OID 116178)
-- Dependencies: 186
-- Data for Name: personnel; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO personnel (id_personnel, id_fonction, id_adresse, id_service, id_structure, nom_prenom, matricule, cni, sexe, date_naissance, lieu_naissance, nationalite, date_recrutement, photo) VALUES (1, 3, 3, 1, 1, 'BOMBA Emile', 'A1245', '122012478', 'Masculin', '2012-06-20', 'Bamenda', 'Camerounais', '2014-10-15', 'Annie.JPG');
INSERT INTO personnel (id_personnel, id_fonction, id_adresse, id_service, id_structure, nom_prenom, matricule, cni, sexe, date_naissance, lieu_naissance, nationalite, date_recrutement, photo) VALUES (3, 3, 5, 2, 1, 'hassan le grand', '000321', '451002001', 'Masculin', '2015-08-04', 'Foumban', 'Nigeriane', '2015-08-07', '1.jpg');
INSERT INTO personnel (id_personnel, id_fonction, id_adresse, id_service, id_structure, nom_prenom, matricule, cni, sexe, date_naissance, lieu_naissance, nationalite, date_recrutement, photo) VALUES (2, 2, 4, 1, 2, 'barack hussein', '000123', '020071209', 'Masculin', '2015-04-13', 'Bertoua', 'Camerounaise', '2015-08-10', 'Koala.jpg');
INSERT INTO personnel (id_personnel, id_fonction, id_adresse, id_service, id_structure, nom_prenom, matricule, cni, sexe, date_naissance, lieu_naissance, nationalite, date_recrutement, photo) VALUES (4, 2, 6, 1, 2, 'Annie Crescence', 'A1325', '0124523', 'Feminin', '2015-06-16', 'Bafoussam', 'Ivoirienne', '2015-08-04', 'Capture.PNG');


--
-- TOC entry 2319 (class 0 OID 116188)
-- Dependencies: 187
-- Data for Name: reforme; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO reforme (id_reforme, id_vehicule, datemisereforme, datevente, montantvente, vendu) VALUES (1, 3, '2015-10-01', '2015-10-06', 500000, true);
INSERT INTO reforme (id_reforme, id_vehicule, datemisereforme, datevente, montantvente, vendu) VALUES (2, 1, '2015-10-06', '2015-10-08', 2000000, true);


--
-- TOC entry 2320 (class 0 OID 116195)
-- Dependencies: 188
-- Data for Name: region; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO region (id_region, id_pays, nom) VALUES (1, 1, 'Ouest');
INSERT INTO region (id_region, id_pays, nom) VALUES (2, 1, 'Centre');
INSERT INTO region (id_region, id_pays, nom) VALUES (3, 1, 'Adamaoua');
INSERT INTO region (id_region, id_pays, nom) VALUES (4, 1, 'Nord');
INSERT INTO region (id_region, id_pays, nom) VALUES (5, 1, 'Sud');
INSERT INTO region (id_region, id_pays, nom) VALUES (6, 1, 'Extreme Nord');
INSERT INTO region (id_region, id_pays, nom) VALUES (7, 1, 'Sud Ouest');
INSERT INTO region (id_region, id_pays, nom) VALUES (8, 1, 'Nord Ouest');
INSERT INTO region (id_region, id_pays, nom) VALUES (9, 1, 'Est');
INSERT INTO region (id_region, id_pays, nom) VALUES (10, 1, 'Littoral');


--
-- TOC entry 2321 (class 0 OID 116198)
-- Dependencies: 189
-- Data for Name: reparation; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO reparation (id_reparation, id_typereparation, id_garage, id_vehicule, datereparation, coutreparation, immobilisation, datedebut, datefin, commentaire) VALUES (1, 1, 1, 3, '2015-10-02', 100000, '', '2015-10-02', '2015-10-06', '');
INSERT INTO reparation (id_reparation, id_typereparation, id_garage, id_vehicule, datereparation, coutreparation, immobilisation, datedebut, datefin, commentaire) VALUES (2, 3, 2, 2, '2015-10-03', 30000, '', '2015-10-03', '2015-10-06', '');


--
-- TOC entry 2322 (class 0 OID 116208)
-- Dependencies: 190
-- Data for Name: restitution; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2323 (class 0 OID 116213)
-- Dependencies: 191
-- Data for Name: roleutilisateur; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2324 (class 0 OID 116219)
-- Dependencies: 192
-- Data for Name: service; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO service (id_service, nom) VALUES (1, 'CPP');
INSERT INTO service (id_service, nom) VALUES (2, 'DRFP');
INSERT INTO service (id_service, nom) VALUES (3, 'DCOOP');


--
-- TOC entry 2325 (class 0 OID 116224)
-- Dependencies: 193
-- Data for Name: sinistre; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO sinistre (id_sinistre, id_typesinistre, id_garage, id_vehicule, datesinistre, lieusinistre, montant, immobilisation, datedebut, datefin, commentaire, photo) VALUES (1, 1, 2, 3, '2015-10-06', 'Djombe', 100000, '', '2015-09-06', '2015-10-01', 'Le vehicule a fait un tonneau', NULL);
INSERT INTO sinistre (id_sinistre, id_typesinistre, id_garage, id_vehicule, datesinistre, lieusinistre, montant, immobilisation, datedebut, datefin, commentaire, photo) VALUES (2, 3, 3, 1, '2015-10-02', 'Makenene', 50000, '', '2015-08-03', '2015-10-03', 'Le vehicule a subit un incendie', NULL);


--
-- TOC entry 2326 (class 0 OID 116234)
-- Dependencies: 194
-- Data for Name: sourceenergie; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO sourceenergie (id_sourceenergie, nom) VALUES (1, 'Essence');
INSERT INTO sourceenergie (id_sourceenergie, nom) VALUES (2, 'Gasoil');
INSERT INTO sourceenergie (id_sourceenergie, nom) VALUES (3, 'Gaz Naturel');
INSERT INTO sourceenergie (id_sourceenergie, nom) VALUES (4, 'Diesel');


--
-- TOC entry 2327 (class 0 OID 116239)
-- Dependencies: 195
-- Data for Name: sourcefinancement; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO sourcefinancement (id_sourcefinancement, nom) VALUES (1, 'Etat');
INSERT INTO sourcefinancement (id_sourcefinancement, nom) VALUES (2, 'Prive');
INSERT INTO sourcefinancement (id_sourcefinancement, nom) VALUES (3, 'Para-Public');
INSERT INTO sourcefinancement (id_sourcefinancement, nom) VALUES (4, 'ONG');


--
-- TOC entry 2328 (class 0 OID 116243)
-- Dependencies: 196
-- Data for Name: statutstructure; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO statutstructure (id_statutstructure, nom) VALUES (1, 'Publique');
INSERT INTO statutstructure (id_statutstructure, nom) VALUES (2, 'Privé');
INSERT INTO statutstructure (id_statutstructure, nom) VALUES (3, 'Para-Public');


--
-- TOC entry 2329 (class 0 OID 116247)
-- Dependencies: 197
-- Data for Name: structure; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO structure (id_structure, id_statutstructure, id_adresse, id_typestructure, id_arrondissement, id_district, id_region, nom, localite, active, gps_nord, gps_sud) VALUES (1, 1, 1, 1, 1, 1, 1, 'Hopital Central de Foumban', 'Foumban', true, NULL, NULL);
INSERT INTO structure (id_structure, id_statutstructure, id_adresse, id_typestructure, id_arrondissement, id_district, id_region, nom, localite, active, gps_nord, gps_sud) VALUES (2, 1, 7, 1, 2, 2, 1, 'Hopital De District de Bafoussam', 'Bafoussam', true, NULL, NULL);


--
-- TOC entry 2330 (class 0 OID 116254)
-- Dependencies: 198
-- Data for Name: typereparation; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO typereparation (id_typereparation, nom) VALUES (1, 'Nouvelle Peinture');
INSERT INTO typereparation (id_typereparation, nom) VALUES (2, 'Remplacement Des Roues');
INSERT INTO typereparation (id_typereparation, nom) VALUES (3, 'Nouveau Moteur');


--
-- TOC entry 2331 (class 0 OID 116257)
-- Dependencies: 199
-- Data for Name: typesinistre; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO typesinistre (id_typesinistre, nom) VALUES (1, 'Exces dee Vitesse');
INSERT INTO typesinistre (id_typesinistre, nom) VALUES (2, 'Bloquage Du Volant');
INSERT INTO typesinistre (id_typesinistre, nom) VALUES (3, 'Incendie');


--
-- TOC entry 2332 (class 0 OID 116261)
-- Dependencies: 200
-- Data for Name: typestructure; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO typestructure (id_typestructure, code_fr, nom_fr) VALUES (1, 'CSI', 'Centre de Sante Integré');
INSERT INTO typestructure (id_typestructure, code_fr, nom_fr) VALUES (2, 'HD', 'Hôpital de District');
INSERT INTO typestructure (id_typestructure, code_fr, nom_fr) VALUES (3, 'HG', 'Hôpital Général');
INSERT INTO typestructure (id_typestructure, code_fr, nom_fr) VALUES (4, 'CMA', 'CENTRE MEDICAL D''ARRONDISSEMENT');


--
-- TOC entry 2333 (class 0 OID 116265)
-- Dependencies: 201
-- Data for Name: utilisateur; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO utilisateur (idutilisateur, nom, prenom, sexe, login, mdp) VALUES (1, 'batrapi', 'batrapi', 'm', 'batrapi', 'batrapi');


--
-- TOC entry 2334 (class 0 OID 116271)
-- Dependencies: 202
-- Data for Name: vehicule; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO vehicule (id_vehicule, id_sourcefinancement, id_modele, id_modeacquisition, id_sourceenergie, id_categorievehicule, immatriculation, numchassis, cartegrise, datemiseenservice, nbredeplace, dateacquisition, numserie, puissance, coutachatttc, description, document, nbresinistre, nbrereparation, nbrerevision, photo, attribution, restitution, etat) VALUES (3, 3, 1, 1, 3, 3, 'SU587', 'Su124574HG', 'FB417854ML', NULL, 3, '2015-09-01', 'SU587', '10000', 25000000, '', NULL, 0, 0, 0, 'HummerH2.jpg', 1, 0, 'Reforme');
INSERT INTO vehicule (id_vehicule, id_sourcefinancement, id_modele, id_modeacquisition, id_sourceenergie, id_categorievehicule, immatriculation, numchassis, cartegrise, datemiseenservice, nbredeplace, dateacquisition, numserie, puissance, coutachatttc, description, document, nbresinistre, nbrereparation, nbrerevision, photo, attribution, restitution, etat) VALUES (2, 1, 2, 1, 2, 1, 'OU 412 VD', 'OU20103DB', 'A210352FV', NULL, 5, '2015-08-03', 'OU54120TGV', '15000', 4500000, '', NULL, 0, 0, 0, 'HUMMER.jpg', 1, 0, 'Fonctionnel');
INSERT INTO vehicule (id_vehicule, id_sourcefinancement, id_modele, id_modeacquisition, id_sourceenergie, id_categorievehicule, immatriculation, numchassis, cartegrise, datemiseenservice, nbredeplace, dateacquisition, numserie, puissance, coutachatttc, description, document, nbresinistre, nbrereparation, nbrerevision, photo, attribution, restitution, etat) VALUES (1, 1, 1, 1, 1, 1, 'CE 182 FD', 'CE182FD', 'C235BD', NULL, 5, '2015-09-02', 'A1457', '15', 6000000, '', NULL, 0, 0, 0, 'Ambulance.jpg', 0, 1, 'Fonctionnel');


--
-- TOC entry 2026 (class 2606 OID 116289)
-- Name: pk_adresse; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY adresse
    ADD CONSTRAINT pk_adresse PRIMARY KEY (id_adresse);


--
-- TOC entry 2029 (class 2606 OID 116291)
-- Name: pk_annee; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY annee
    ADD CONSTRAINT pk_annee PRIMARY KEY (id_annee);


--
-- TOC entry 2032 (class 2606 OID 116293)
-- Name: pk_arrondissement; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY arrondissement
    ADD CONSTRAINT pk_arrondissement PRIMARY KEY (id_arrondissement);


--
-- TOC entry 2037 (class 2606 OID 116295)
-- Name: pk_attribution; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY attribution
    ADD CONSTRAINT pk_attribution PRIMARY KEY (id_attribution);


--
-- TOC entry 2041 (class 2606 OID 116297)
-- Name: pk_categorievehicule; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY categorievehicule
    ADD CONSTRAINT pk_categorievehicule PRIMARY KEY (id_categorievehicule);


--
-- TOC entry 2045 (class 2606 OID 116299)
-- Name: pk_demande; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY demande
    ADD CONSTRAINT pk_demande PRIMARY KEY (id_demande);


--
-- TOC entry 2050 (class 2606 OID 116301)
-- Name: pk_departement; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY departement
    ADD CONSTRAINT pk_departement PRIMARY KEY (id_departement);


--
-- TOC entry 2053 (class 2606 OID 116303)
-- Name: pk_district; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY district
    ADD CONSTRAINT pk_district PRIMARY KEY (id_district);


--
-- TOC entry 2056 (class 2606 OID 116305)
-- Name: pk_fonction; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY fonction
    ADD CONSTRAINT pk_fonction PRIMARY KEY (id_fonction);


--
-- TOC entry 2060 (class 2606 OID 116307)
-- Name: pk_garage; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY garage
    ADD CONSTRAINT pk_garage PRIMARY KEY (id_garage);


--
-- TOC entry 2063 (class 2606 OID 116309)
-- Name: pk_marque; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY marque
    ADD CONSTRAINT pk_marque PRIMARY KEY (id_marque);


--
-- TOC entry 2066 (class 2606 OID 116311)
-- Name: pk_modeacquisition; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY modeacquisition
    ADD CONSTRAINT pk_modeacquisition PRIMARY KEY (id_modeacquisition);


--
-- TOC entry 2069 (class 2606 OID 116313)
-- Name: pk_modele; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY modele
    ADD CONSTRAINT pk_modele PRIMARY KEY (id_modele);


--
-- TOC entry 2073 (class 2606 OID 116315)
-- Name: pk_mois; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY mois
    ADD CONSTRAINT pk_mois PRIMARY KEY (id_mois);


--
-- TOC entry 2078 (class 2606 OID 116317)
-- Name: pk_norme; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY norme
    ADD CONSTRAINT pk_norme PRIMARY KEY (id_norme);


--
-- TOC entry 2081 (class 2606 OID 116319)
-- Name: pk_pays; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY pays
    ADD CONSTRAINT pk_pays PRIMARY KEY (id_pays);


--
-- TOC entry 2086 (class 2606 OID 116321)
-- Name: pk_personnel; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY personnel
    ADD CONSTRAINT pk_personnel PRIMARY KEY (id_personnel);


--
-- TOC entry 2090 (class 2606 OID 116323)
-- Name: pk_reforme; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY reforme
    ADD CONSTRAINT pk_reforme PRIMARY KEY (id_reforme);


--
-- TOC entry 2093 (class 2606 OID 116325)
-- Name: pk_region; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY region
    ADD CONSTRAINT pk_region PRIMARY KEY (id_region);


--
-- TOC entry 2098 (class 2606 OID 116327)
-- Name: pk_reparation; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY reparation
    ADD CONSTRAINT pk_reparation PRIMARY KEY (id_reparation);


--
-- TOC entry 2106 (class 2606 OID 116329)
-- Name: pk_restitution; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY restitution
    ADD CONSTRAINT pk_restitution PRIMARY KEY (id_restitution);


--
-- TOC entry 2110 (class 2606 OID 116331)
-- Name: pk_roleutilisateur; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY roleutilisateur
    ADD CONSTRAINT pk_roleutilisateur PRIMARY KEY (idroleutilisateur);


--
-- TOC entry 2113 (class 2606 OID 116333)
-- Name: pk_service; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY service
    ADD CONSTRAINT pk_service PRIMARY KEY (id_service);


--
-- TOC entry 2117 (class 2606 OID 116335)
-- Name: pk_sinistre; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sinistre
    ADD CONSTRAINT pk_sinistre PRIMARY KEY (id_sinistre);


--
-- TOC entry 2122 (class 2606 OID 116337)
-- Name: pk_sourceenergie; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sourceenergie
    ADD CONSTRAINT pk_sourceenergie PRIMARY KEY (id_sourceenergie);


--
-- TOC entry 2125 (class 2606 OID 116339)
-- Name: pk_sourcefinancement; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY sourcefinancement
    ADD CONSTRAINT pk_sourcefinancement PRIMARY KEY (id_sourcefinancement);


--
-- TOC entry 2128 (class 2606 OID 116341)
-- Name: pk_statutstructure; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY statutstructure
    ADD CONSTRAINT pk_statutstructure PRIMARY KEY (id_statutstructure);


--
-- TOC entry 2135 (class 2606 OID 116343)
-- Name: pk_structure; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY structure
    ADD CONSTRAINT pk_structure PRIMARY KEY (id_structure);


--
-- TOC entry 2138 (class 2606 OID 116345)
-- Name: pk_typereparation; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY typereparation
    ADD CONSTRAINT pk_typereparation PRIMARY KEY (id_typereparation);


--
-- TOC entry 2141 (class 2606 OID 116347)
-- Name: pk_typesinistre; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY typesinistre
    ADD CONSTRAINT pk_typesinistre PRIMARY KEY (id_typesinistre);


--
-- TOC entry 2144 (class 2606 OID 116349)
-- Name: pk_typestructure; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY typestructure
    ADD CONSTRAINT pk_typestructure PRIMARY KEY (id_typestructure);


--
-- TOC entry 2147 (class 2606 OID 116351)
-- Name: pk_utilisateur; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY utilisateur
    ADD CONSTRAINT pk_utilisateur PRIMARY KEY (idutilisateur);


--
-- TOC entry 2155 (class 2606 OID 116353)
-- Name: pk_vehicule; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY vehicule
    ADD CONSTRAINT pk_vehicule PRIMARY KEY (id_vehicule);


--
-- TOC entry 2024 (class 1259 OID 116354)
-- Name: adresse_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX adresse_pk ON adresse USING btree (id_adresse);


--
-- TOC entry 2027 (class 1259 OID 116355)
-- Name: annee_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX annee_pk ON annee USING btree (id_annee);


--
-- TOC entry 2030 (class 1259 OID 116356)
-- Name: arrondissement_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX arrondissement_pk ON arrondissement USING btree (id_arrondissement);


--
-- TOC entry 2108 (class 1259 OID 116357)
-- Name: association15_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association15_fk ON roleutilisateur USING btree (idutilisateur);


--
-- TOC entry 2130 (class 1259 OID 116358)
-- Name: association33_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association33_fk ON structure USING btree (id_typestructure);


--
-- TOC entry 2074 (class 1259 OID 116359)
-- Name: association34_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association34_fk ON norme USING btree (id_typestructure);


--
-- TOC entry 2075 (class 1259 OID 116360)
-- Name: association35_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association35_fk ON norme USING btree (id_categorievehicule);


--
-- TOC entry 2149 (class 1259 OID 116361)
-- Name: association36_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association36_fk ON vehicule USING btree (id_modeacquisition);


--
-- TOC entry 2150 (class 1259 OID 116362)
-- Name: association370_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association370_fk ON vehicule USING btree (id_categorievehicule);


--
-- TOC entry 2096 (class 1259 OID 116363)
-- Name: association371_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association371_fk ON reparation USING btree (id_vehicule);


--
-- TOC entry 2151 (class 1259 OID 116364)
-- Name: association372_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association372_fk ON vehicule USING btree (id_modele);


--
-- TOC entry 2034 (class 1259 OID 116365)
-- Name: association373_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association373_fk ON attribution USING btree (id_vehicule);


--
-- TOC entry 2115 (class 1259 OID 116366)
-- Name: association374_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association374_fk ON sinistre USING btree (id_vehicule);


--
-- TOC entry 2152 (class 1259 OID 116367)
-- Name: association375_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association375_fk ON vehicule USING btree (id_sourcefinancement);


--
-- TOC entry 2153 (class 1259 OID 116368)
-- Name: association37_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association37_fk ON vehicule USING btree (id_sourceenergie);


--
-- TOC entry 2088 (class 1259 OID 116369)
-- Name: association38_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association38_fk ON reforme USING btree (id_vehicule);


--
-- TOC entry 2102 (class 1259 OID 116370)
-- Name: association39_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association39_fk ON restitution USING btree (id_vehicule);


--
-- TOC entry 2103 (class 1259 OID 116371)
-- Name: association40_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association40_fk ON restitution USING btree (id_attribution);


--
-- TOC entry 2104 (class 1259 OID 116372)
-- Name: association41_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association41_fk ON restitution USING btree (id_personnel);


--
-- TOC entry 2131 (class 1259 OID 116373)
-- Name: association422_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association422_fk ON structure USING btree (id_arrondissement);


--
-- TOC entry 2132 (class 1259 OID 116374)
-- Name: association42_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association42_fk ON structure USING btree (id_adresse);


--
-- TOC entry 2042 (class 1259 OID 116375)
-- Name: association43_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association43_fk ON demande USING btree (id_structure);


--
-- TOC entry 2082 (class 1259 OID 116376)
-- Name: association44_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association44_fk ON personnel USING btree (id_adresse);


--
-- TOC entry 2057 (class 1259 OID 116377)
-- Name: association45_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association45_fk ON garage USING btree (id_adresse);


--
-- TOC entry 2133 (class 1259 OID 116378)
-- Name: association48_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association48_fk ON structure USING btree (id_statutstructure);


--
-- TOC entry 2083 (class 1259 OID 116379)
-- Name: association51_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX association51_fk ON personnel USING btree (id_service);


--
-- TOC entry 2035 (class 1259 OID 116380)
-- Name: attribution_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX attribution_pk ON attribution USING btree (id_attribution);


--
-- TOC entry 2039 (class 1259 OID 116381)
-- Name: categorievehicule_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX categorievehicule_pk ON categorievehicule USING btree (id_categorievehicule);


--
-- TOC entry 2043 (class 1259 OID 116382)
-- Name: demande_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX demande_pk ON demande USING btree (id_demande);


--
-- TOC entry 2048 (class 1259 OID 116383)
-- Name: departement_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX departement_pk ON departement USING btree (id_departement);


--
-- TOC entry 2054 (class 1259 OID 116384)
-- Name: fonction_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX fonction_pk ON fonction USING btree (id_fonction);


--
-- TOC entry 2058 (class 1259 OID 116385)
-- Name: garage_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX garage_pk ON garage USING btree (id_garage);


--
-- TOC entry 2061 (class 1259 OID 116386)
-- Name: marque_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX marque_pk ON marque USING btree (id_marque);


--
-- TOC entry 2064 (class 1259 OID 116387)
-- Name: modeacquisition_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX modeacquisition_pk ON modeacquisition USING btree (id_modeacquisition);


--
-- TOC entry 2067 (class 1259 OID 116388)
-- Name: modele_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX modele_pk ON modele USING btree (id_modele);


--
-- TOC entry 2071 (class 1259 OID 116389)
-- Name: mois_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX mois_pk ON mois USING btree (id_mois);


--
-- TOC entry 2076 (class 1259 OID 116390)
-- Name: norme_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX norme_pk ON norme USING btree (id_norme);


--
-- TOC entry 2079 (class 1259 OID 116391)
-- Name: pays_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX pays_pk ON pays USING btree (id_pays);


--
-- TOC entry 2084 (class 1259 OID 116392)
-- Name: personnel_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX personnel_pk ON personnel USING btree (id_personnel);


--
-- TOC entry 2087 (class 1259 OID 116393)
-- Name: reference12_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX reference12_fk ON personnel USING btree (id_fonction);


--
-- TOC entry 2099 (class 1259 OID 116394)
-- Name: reference16_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX reference16_fk ON reparation USING btree (id_garage);


--
-- TOC entry 2118 (class 1259 OID 116395)
-- Name: reference18_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX reference18_fk ON sinistre USING btree (id_garage);


--
-- TOC entry 2070 (class 1259 OID 116396)
-- Name: reference20_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX reference20_fk ON modele USING btree (id_marque);


--
-- TOC entry 2046 (class 1259 OID 116397)
-- Name: reference22_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX reference22_fk ON demande USING btree (id_modele);


--
-- TOC entry 2094 (class 1259 OID 116398)
-- Name: reference24_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX reference24_fk ON region USING btree (id_pays);


--
-- TOC entry 2038 (class 1259 OID 116399)
-- Name: reference26_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX reference26_fk ON attribution USING btree (id_personnel);


--
-- TOC entry 2051 (class 1259 OID 116400)
-- Name: reference29_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX reference29_fk ON departement USING btree (id_region);


--
-- TOC entry 2100 (class 1259 OID 116401)
-- Name: reference41_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX reference41_fk ON reparation USING btree (id_typereparation);


--
-- TOC entry 2119 (class 1259 OID 116402)
-- Name: reference43_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX reference43_fk ON sinistre USING btree (id_typesinistre);


--
-- TOC entry 2047 (class 1259 OID 116403)
-- Name: reference4_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX reference4_fk ON demande USING btree (id_categorievehicule);


--
-- TOC entry 2033 (class 1259 OID 116404)
-- Name: reference8_fk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX reference8_fk ON arrondissement USING btree (id_departement);


--
-- TOC entry 2091 (class 1259 OID 116405)
-- Name: reforme_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX reforme_pk ON reforme USING btree (id_reforme);


--
-- TOC entry 2095 (class 1259 OID 116406)
-- Name: region_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX region_pk ON region USING btree (id_region);


--
-- TOC entry 2101 (class 1259 OID 116407)
-- Name: reparation_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX reparation_pk ON reparation USING btree (id_reparation);


--
-- TOC entry 2107 (class 1259 OID 116408)
-- Name: restitution_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX restitution_pk ON restitution USING btree (id_restitution);


--
-- TOC entry 2111 (class 1259 OID 116409)
-- Name: roleutilisateur_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX roleutilisateur_pk ON roleutilisateur USING btree (idroleutilisateur);


--
-- TOC entry 2114 (class 1259 OID 116410)
-- Name: service_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX service_pk ON service USING btree (id_service);


--
-- TOC entry 2120 (class 1259 OID 116411)
-- Name: sinistre_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX sinistre_pk ON sinistre USING btree (id_sinistre);


--
-- TOC entry 2123 (class 1259 OID 116412)
-- Name: sourceenergie_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX sourceenergie_pk ON sourceenergie USING btree (id_sourceenergie);


--
-- TOC entry 2126 (class 1259 OID 116413)
-- Name: sourcefinancement_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX sourcefinancement_pk ON sourcefinancement USING btree (id_sourcefinancement);


--
-- TOC entry 2129 (class 1259 OID 116414)
-- Name: statutstructure_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX statutstructure_pk ON statutstructure USING btree (id_statutstructure);


--
-- TOC entry 2136 (class 1259 OID 116415)
-- Name: structure_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX structure_pk ON structure USING btree (id_structure);


--
-- TOC entry 2139 (class 1259 OID 116416)
-- Name: typereparation_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX typereparation_pk ON typereparation USING btree (id_typereparation);


--
-- TOC entry 2142 (class 1259 OID 116417)
-- Name: typesinistre_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX typesinistre_pk ON typesinistre USING btree (id_typesinistre);


--
-- TOC entry 2145 (class 1259 OID 116418)
-- Name: typestructure_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX typestructure_pk ON typestructure USING btree (id_typestructure);


--
-- TOC entry 2148 (class 1259 OID 116419)
-- Name: utilisateur_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX utilisateur_pk ON utilisateur USING btree (idutilisateur);


--
-- TOC entry 2156 (class 1259 OID 116420)
-- Name: vehicule_pk; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX vehicule_pk ON vehicule USING btree (id_vehicule);


--
-- TOC entry 2157 (class 2606 OID 116421)
-- Name: fk_arrondis_reference_departem; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY arrondissement
    ADD CONSTRAINT fk_arrondis_reference_departem FOREIGN KEY (id_departement) REFERENCES departement(id_departement) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2158 (class 2606 OID 116426)
-- Name: fk_attribut_associati_vehicule; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY attribution
    ADD CONSTRAINT fk_attribut_associati_vehicule FOREIGN KEY (id_vehicule) REFERENCES vehicule(id_vehicule) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2159 (class 2606 OID 116431)
-- Name: fk_attribut_reference_personne; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY attribution
    ADD CONSTRAINT fk_attribut_reference_personne FOREIGN KEY (id_personnel) REFERENCES personnel(id_personnel) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2160 (class 2606 OID 116436)
-- Name: fk_demande_associati_structur; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY demande
    ADD CONSTRAINT fk_demande_associati_structur FOREIGN KEY (id_structure) REFERENCES structure(id_structure) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2161 (class 2606 OID 116441)
-- Name: fk_demande_reference_categori; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY demande
    ADD CONSTRAINT fk_demande_reference_categori FOREIGN KEY (id_categorievehicule) REFERENCES categorievehicule(id_categorievehicule) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2162 (class 2606 OID 116446)
-- Name: fk_demande_reference_modele; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY demande
    ADD CONSTRAINT fk_demande_reference_modele FOREIGN KEY (id_modele) REFERENCES modele(id_modele) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2163 (class 2606 OID 116451)
-- Name: fk_departem_reference_region; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY departement
    ADD CONSTRAINT fk_departem_reference_region FOREIGN KEY (id_region) REFERENCES region(id_region) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2164 (class 2606 OID 116456)
-- Name: fk_garage_associati_adresse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY garage
    ADD CONSTRAINT fk_garage_associati_adresse FOREIGN KEY (id_adresse) REFERENCES adresse(id_adresse) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2165 (class 2606 OID 116461)
-- Name: fk_modele_reference_marque; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY modele
    ADD CONSTRAINT fk_modele_reference_marque FOREIGN KEY (id_marque) REFERENCES marque(id_marque) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2166 (class 2606 OID 116466)
-- Name: fk_norme_associati_categori; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY norme
    ADD CONSTRAINT fk_norme_associati_categori FOREIGN KEY (id_categorievehicule) REFERENCES categorievehicule(id_categorievehicule) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2167 (class 2606 OID 116471)
-- Name: fk_norme_associati_typestru; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY norme
    ADD CONSTRAINT fk_norme_associati_typestru FOREIGN KEY (id_typestructure) REFERENCES typestructure(id_typestructure) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2168 (class 2606 OID 116476)
-- Name: fk_personne_associati_adresse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY personnel
    ADD CONSTRAINT fk_personne_associati_adresse FOREIGN KEY (id_adresse) REFERENCES adresse(id_adresse) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2169 (class 2606 OID 116481)
-- Name: fk_personne_associati_service; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY personnel
    ADD CONSTRAINT fk_personne_associati_service FOREIGN KEY (id_service) REFERENCES service(id_service) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2170 (class 2606 OID 116486)
-- Name: fk_personne_reference_fonction; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY personnel
    ADD CONSTRAINT fk_personne_reference_fonction FOREIGN KEY (id_fonction) REFERENCES fonction(id_fonction) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2171 (class 2606 OID 116491)
-- Name: fk_personne_reference_structur; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY personnel
    ADD CONSTRAINT fk_personne_reference_structur FOREIGN KEY (id_structure) REFERENCES structure(id_structure) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2172 (class 2606 OID 116496)
-- Name: fk_reforme_associati_vehicule; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY reforme
    ADD CONSTRAINT fk_reforme_associati_vehicule FOREIGN KEY (id_vehicule) REFERENCES vehicule(id_vehicule) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2173 (class 2606 OID 116501)
-- Name: fk_region_reference_pays; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY region
    ADD CONSTRAINT fk_region_reference_pays FOREIGN KEY (id_pays) REFERENCES pays(id_pays) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2174 (class 2606 OID 116506)
-- Name: fk_reparati_associati_vehicule; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY reparation
    ADD CONSTRAINT fk_reparati_associati_vehicule FOREIGN KEY (id_vehicule) REFERENCES vehicule(id_vehicule) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2175 (class 2606 OID 116511)
-- Name: fk_reparati_reference_garage; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY reparation
    ADD CONSTRAINT fk_reparati_reference_garage FOREIGN KEY (id_garage) REFERENCES garage(id_garage) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2176 (class 2606 OID 116516)
-- Name: fk_reparati_reference_typerepa; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY reparation
    ADD CONSTRAINT fk_reparati_reference_typerepa FOREIGN KEY (id_typereparation) REFERENCES typereparation(id_typereparation) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2177 (class 2606 OID 116521)
-- Name: fk_restitut_associati_attribut; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY restitution
    ADD CONSTRAINT fk_restitut_associati_attribut FOREIGN KEY (id_attribution) REFERENCES attribution(id_attribution) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2178 (class 2606 OID 116526)
-- Name: fk_restitut_associati_personne; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY restitution
    ADD CONSTRAINT fk_restitut_associati_personne FOREIGN KEY (id_personnel) REFERENCES personnel(id_personnel) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2179 (class 2606 OID 116531)
-- Name: fk_restitut_associati_vehicule; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY restitution
    ADD CONSTRAINT fk_restitut_associati_vehicule FOREIGN KEY (id_vehicule) REFERENCES vehicule(id_vehicule) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2180 (class 2606 OID 116536)
-- Name: fk_roleutil_associati_utilisat; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY roleutilisateur
    ADD CONSTRAINT fk_roleutil_associati_utilisat FOREIGN KEY (idutilisateur) REFERENCES utilisateur(idutilisateur) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2181 (class 2606 OID 116541)
-- Name: fk_sinistre_associati_vehicule; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sinistre
    ADD CONSTRAINT fk_sinistre_associati_vehicule FOREIGN KEY (id_vehicule) REFERENCES vehicule(id_vehicule) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2182 (class 2606 OID 116546)
-- Name: fk_sinistre_reference_garage; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sinistre
    ADD CONSTRAINT fk_sinistre_reference_garage FOREIGN KEY (id_garage) REFERENCES garage(id_garage) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2183 (class 2606 OID 116551)
-- Name: fk_sinistre_reference_typesini; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sinistre
    ADD CONSTRAINT fk_sinistre_reference_typesini FOREIGN KEY (id_typesinistre) REFERENCES typesinistre(id_typesinistre) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2184 (class 2606 OID 116556)
-- Name: fk_structur_associati_adresse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY structure
    ADD CONSTRAINT fk_structur_associati_adresse FOREIGN KEY (id_adresse) REFERENCES adresse(id_adresse) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2185 (class 2606 OID 116561)
-- Name: fk_structur_associati_arrondis; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY structure
    ADD CONSTRAINT fk_structur_associati_arrondis FOREIGN KEY (id_arrondissement) REFERENCES arrondissement(id_arrondissement) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2186 (class 2606 OID 116566)
-- Name: fk_structur_associati_statutst; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY structure
    ADD CONSTRAINT fk_structur_associati_statutst FOREIGN KEY (id_statutstructure) REFERENCES statutstructure(id_statutstructure) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2187 (class 2606 OID 116571)
-- Name: fk_structur_associati_typestru; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY structure
    ADD CONSTRAINT fk_structur_associati_typestru FOREIGN KEY (id_typestructure) REFERENCES typestructure(id_typestructure) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2188 (class 2606 OID 116576)
-- Name: fk_structur_reference_district; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY structure
    ADD CONSTRAINT fk_structur_reference_district FOREIGN KEY (id_district) REFERENCES district(id_district) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2189 (class 2606 OID 116581)
-- Name: fk_structur_reference_region; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY structure
    ADD CONSTRAINT fk_structur_reference_region FOREIGN KEY (id_region) REFERENCES region(id_region) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2190 (class 2606 OID 116586)
-- Name: fk_vehicule_associati_categori; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY vehicule
    ADD CONSTRAINT fk_vehicule_associati_categori FOREIGN KEY (id_categorievehicule) REFERENCES categorievehicule(id_categorievehicule) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2191 (class 2606 OID 116591)
-- Name: fk_vehicule_associati_modeacqu; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY vehicule
    ADD CONSTRAINT fk_vehicule_associati_modeacqu FOREIGN KEY (id_modeacquisition) REFERENCES modeacquisition(id_modeacquisition) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2192 (class 2606 OID 116596)
-- Name: fk_vehicule_associati_modele; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY vehicule
    ADD CONSTRAINT fk_vehicule_associati_modele FOREIGN KEY (id_modele) REFERENCES modele(id_modele) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2193 (class 2606 OID 116601)
-- Name: fk_vehicule_associati_sourceen; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY vehicule
    ADD CONSTRAINT fk_vehicule_associati_sourceen FOREIGN KEY (id_sourceenergie) REFERENCES sourceenergie(id_sourceenergie) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2194 (class 2606 OID 116606)
-- Name: fk_vehicule_associati_sourcefi; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY vehicule
    ADD CONSTRAINT fk_vehicule_associati_sourcefi FOREIGN KEY (id_sourcefinancement) REFERENCES sourcefinancement(id_sourcefinancement) ON UPDATE RESTRICT ON DELETE RESTRICT;


-- Completed on 2015-11-26 12:46:48

--
-- PostgreSQL database dump complete
--

