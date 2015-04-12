# Clasified

Ovo je Web Aplikacija napravljena kao projekat za predmet Veb Sistemi na Fakultetu Informacionih Tehnologija, Univerzitet Metropolitan.

#Baza

Projekat koristi Hibernate ORM i MySQL utf-8 bazu. Pri promeni baze potrebno je izmeniti hibernate.cfg.xml file i CleanUpAndSaveServiceImpl
. Tabela se zove clasified_production a default username i password su root i root. Za second level chache se koristi EhChache a za pooling konekcija na bazu se koristi c3p0.

#Jezici

Postoji podrska za Kineski i Engleski jezik. Jezici se menjaju u AppModule file-u, a koriste properties file-ove te je lako moguce dodati nove jezike.

#Placanje

Postoji integracija sa PayPal-om. Admin sajta moze da unese svoje PayPal podatke i njima plati. Podrzana su oba moda i LIVE i SANDBOX.

#Automatizacija

Postoji automatizacija i automatski backup koji se rade svakih 24h.

#Admin Panel

Admin moze da edituje skoro sve aspekte sajta iz admin panela, moze da dodaje/uklanja/menja/pretrazuje proizvode, kategorije, podkategorije, reklame, i sve stranice sajta.
Admin takodje moze da bira koje je sve podatke moguce dodati prilikom ubacivanja reklama.

#Dodatne Opcije

-Reklame pamte IP adresu osobe koja ih je dodala.
-Reklame mogu da imaju slike.
-Reklame mogu da imaju video klipove.
-Korisnici mogu da edituju profil
-Korisnici mogu da Bump-uju svoju reklamu na vrh liste
-Korisnici mogu da plate za dodatne opcije reklama (TopAd i FeaturedAd)
-Korisnici mogu da dodaju reklame u Watchlist
-Admin moze da skida MySQL dump baze po potrebi
