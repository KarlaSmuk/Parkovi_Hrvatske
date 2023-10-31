--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: imazivotinju; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.imazivotinju (
    sifpark integer NOT NULL,
    sifzivotinja integer NOT NULL
);


ALTER TABLE public.imazivotinju OWNER TO postgres;

--
-- Name: najvisi_vrhovi; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.najvisi_vrhovi (
    sifvrh integer NOT NULL,
    nazvrh character varying NOT NULL,
    visina integer NOT NULL
);


ALTER TABLE public.najvisi_vrhovi OWNER TO postgres;

--
-- Name: nalazise; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nalazise (
    sifpark integer NOT NULL,
    sifzupanija integer NOT NULL
);


ALTER TABLE public.nalazise OWNER TO postgres;

--
-- Name: parkovi; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.parkovi (
    sifpark integer NOT NULL,
    nazpark character varying NOT NULL,
    godosnutka integer NOT NULL,
    povrsina double precision NOT NULL,
    nazatrakcija character varying NOT NULL,
    nazdoggod character varying NOT NULL,
    siftippark integer NOT NULL,
    sifvrh integer
);


ALTER TABLE public.parkovi OWNER TO postgres;

--
-- Name: tipovi_parka; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipovi_parka (
    siftippark integer NOT NULL,
    naztippark character varying NOT NULL
);


ALTER TABLE public.tipovi_parka OWNER TO postgres;

--
-- Name: zivotinje; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.zivotinje (
    sifzivotinja integer NOT NULL,
    nazzivotinja character varying NOT NULL,
    vrstazivotinja character varying NOT NULL
);


ALTER TABLE public.zivotinje OWNER TO postgres;

--
-- Name: zupanije; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.zupanije (
    sifzupanija integer NOT NULL,
    nazzupanija character varying NOT NULL
);


ALTER TABLE public.zupanije OWNER TO postgres;

--
-- Data for Name: imazivotinju; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.imazivotinju (sifpark, sifzivotinja) FROM stdin;
1	1
1	2
2	11
2	12
3	4
3	5
3	6
4	13
4	14
5	1
5	5
5	6
6	1
6	15
7	8
7	9
8	5
8	7
8	10
9	8
9	10
10	3
10	16
\.


--
-- Data for Name: najvisi_vrhovi; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.najvisi_vrhovi (sifvrh, nazvrh, visina) FROM stdin;
1	Seliški vrh	1279
2	Veliki risnjak	1528
3	Sveti Jure	1762
4	Sljeme	1035
5	Papuk	953
\.


--
-- Data for Name: nalazise; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.nalazise (sifpark, sifzupanija) FROM stdin;
1	15
2	19
3	8
4	18
5	9
5	4
6	14
7	10
7	11
8	17
9	21
9	1
9	2
10	13
10	15
\.


--
-- Data for Name: parkovi; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.parkovi (sifpark, nazpark, godosnutka, povrsina, nazatrakcija, nazdoggod, siftippark, sifvrh) FROM stdin;
1	Krka	1985	109	Skradinski buk	Krkino kulturno ljeto	1	\N
2	Mljet	1960	53.7	Otočić Svete Marije	Obilježavanje godišnjice osnivanja	1	\N
3	Risnjak	1953	63.5	Izvor Kupe	Furmanski dan u kolovozu	1	2
4	Brijuni	1983	33.9	Vidikovac na Ciprovcu	Dani dinosaura u travnju	1	\N
5	Plitvička jezera	1949	296.8	Gornja jezera	Plitvički maraton u lipnju	1	1
6	Kopački rit	1976	231	Šetnica bijelog lopoča	Dan otvorenih vrata u srpnju	2	\N
7	Papuk	1999	336	Drenovačka utvrda Klak	Srednjovjekovni viteški turnir	2	5
8	Biokovo	1981	194	Vidikovac Sv. Jure	Međunarodna brdska utrka u svibnju	2	3
9	Medvednica	1981	179.4	Petsto Horvatovih stuba	Snježna kraljica	2	4
10	Vransko jezero	1999	57	Kasnoantička kula Osridak	Sajam lokalnih proizvoda	2	\N
\.


--
-- Data for Name: tipovi_parka; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tipovi_parka (siftippark, naztippark) FROM stdin;
1	Nacionalni park
2	Park prirode
\.


--
-- Data for Name: zivotinje; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.zivotinje (sifzivotinja, nazzivotinja, vrstazivotinja) FROM stdin;
1	Vidra	sisavac
2	Čovječja ribica	vodozemac
3	Čaplja	ptica
4	Ris	sisavac
5	Vuk	sisavac
6	Medvjed	sisavac
7	Muflon	sisavac
8	Močvarni plavac	leptir
9	Pjegavi daždevnjak	vodozemac
10	Poskok	zmija
11	Sredozemni galeb	ptica
12	Morski vranac	ptica
13	Dupin	sisavac
14	Morska kornjača	gmaz
15	Orao štekavac	ptica
16	Kravosas	zmija
\.


--
-- Data for Name: zupanije; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.zupanije (sifzupanija, nazzupanija) FROM stdin;
0	Nepoznata županija
1	Zagrebačka
10	Virovitičko-podravska
11	Požeško-slavonska
12	Brodsko-posavska
13	Zadarska
14	Osječko-baranjska
15	Šibensko-kninska
16	Vukovarsko-srijemska
17	Splitsko-dalmatinska
18	Istarska
19	Dubrovačko-neretvanska
2	Krapinsko-zagorska
20	Međimurska
21	Grad Zagreb
3	Sisačko-moslavačka
4	Karlovačka
5	Varaždinska
6	Koprivničko-križevačka
7	Bjelovarsko-bilogorska
8	Primorsko-goranska
9	Ličko-senjska
\.


--
-- Name: imazivotinju imazivotinju_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.imazivotinju
    ADD CONSTRAINT imazivotinju_pkey PRIMARY KEY (sifpark, sifzivotinja);


--
-- Name: najvisi_vrhovi najvisi_vrhovi_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.najvisi_vrhovi
    ADD CONSTRAINT najvisi_vrhovi_pkey PRIMARY KEY (sifvrh);


--
-- Name: nalazise nalazise_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nalazise
    ADD CONSTRAINT nalazise_pkey PRIMARY KEY (sifpark, sifzupanija);


--
-- Name: parkovi parkovi_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parkovi
    ADD CONSTRAINT parkovi_pkey PRIMARY KEY (sifpark);


--
-- Name: tipovi_parka tipovi_parka_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipovi_parka
    ADD CONSTRAINT tipovi_parka_pkey PRIMARY KEY (siftippark);


--
-- Name: zivotinje zivotinje_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zivotinje
    ADD CONSTRAINT zivotinje_pkey PRIMARY KEY (sifzivotinja);


--
-- Name: zupanije zupanije_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zupanije
    ADD CONSTRAINT zupanije_pkey PRIMARY KEY (sifzupanija);


--
-- Name: imazivotinju imazivotinju_sifpark_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.imazivotinju
    ADD CONSTRAINT imazivotinju_sifpark_fkey FOREIGN KEY (sifpark) REFERENCES public.parkovi(sifpark);


--
-- Name: imazivotinju imazivotinju_sifzivotinja_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.imazivotinju
    ADD CONSTRAINT imazivotinju_sifzivotinja_fkey FOREIGN KEY (sifzivotinja) REFERENCES public.zivotinje(sifzivotinja);


--
-- Name: nalazise nalazise_sifpark_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nalazise
    ADD CONSTRAINT nalazise_sifpark_fkey FOREIGN KEY (sifpark) REFERENCES public.parkovi(sifpark);


--
-- Name: nalazise nalazise_sifzupanija_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nalazise
    ADD CONSTRAINT nalazise_sifzupanija_fkey FOREIGN KEY (sifzupanija) REFERENCES public.zupanije(sifzupanija);


--
-- Name: parkovi parkovi_siftippark_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parkovi
    ADD CONSTRAINT parkovi_siftippark_fkey FOREIGN KEY (siftippark) REFERENCES public.tipovi_parka(siftippark);


--
-- Name: parkovi parkovi_sifvrh_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parkovi
    ADD CONSTRAINT parkovi_sifvrh_fkey FOREIGN KEY (sifvrh) REFERENCES public.najvisi_vrhovi(sifvrh);


--
-- PostgreSQL database dump complete
--

