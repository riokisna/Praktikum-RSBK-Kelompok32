<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>Tahun Depan Wisuda</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
  </head>
  <body>
    <header>
      <h1 style="text-align:center">SEMANGAT, TAHUN DEPAN WISUDA</h1>
    </header>
    <main style="display:flex;justify-content:center;flex-direction: column;">
            <form action="tambah" method="post" style="display:flex;justify-content:center;flex-direction: column;align-items: center;">
                <label for="nama" style="margin-bottom:5px">Nama Mahasiswa</label>
                <input id="nama" type="text" name="nama" placeholder="Nama Mahasiswa">
                <br>
                <label for="nim" style="margin-bottom:5px">NIM Mahasiswa</label>
                <input id="nim" type="text" name="nim" placeholder="Nim Mahasiswa">
                <br>
                <input type="submit" name="submit" value="Tambah" style="width:150px">
                <label style="text-align:center;"><a href="search" style="text-decoration:none"><< BACK >></a></label>
            </form>
    </main><br>
    <footer style="text-align:center">&copy; Copyright Praktikum Rekayasa Perangkat Lunak 2019</footer>
  </body>
</html>
