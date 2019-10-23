<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>Mesin Pencari Mahasiswa</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
  </head>
  <body style="background-color: #b3ffec">
    <header>
      <h1 style="text-align:center; background-color: #33ffcc;">MESIN PENCARIAN MAHASISWA YANG SEGERA WISUDA</h1>
    </header>
    <main style="display:flex;justify-content:center;flex-direction: column;">
            <form action="search" method="post" style="display:flex;justify-content:center;flex-direction: column;align-items: center;">
                <h2 for="nama" style="margin-bottom:5px">----------------------------Cari Mahasiswa----------------------------</h2>
                <input id="nama" type="text" name="param" style="background-color: white;color: #3CBC8D;border: none;width: 90%;padding: 12px 20px; margin: 8px 0; box-sizing: border-box;border-radius: 3px;" placeholder="Masukan Nama / NIM">
                <span style="color:red ;font-size: 20px">${show}</span>
                <br>
                <input type="submit" name="submit" value="Cari" style="  background-color: #4CAF50; border: none; color: white; padding: 16px 32px; text-decoration: none; margin: 4px 2px; cursor: pointer; width: 90%">
            </form>
                <br>
            <form action="search" method="get" style="display:flex;justify-content:center;flex-direction: column;align-items: center;">
                <h2 for="nama" style="margin-bottom:5px">----------------------------Tambah Mahasiswa----------------------------</h2>
                <input id="nama1" type="text" name="nama1" style="background-color: white;color: #3CBC8D;border: none;width: 90%;padding: 12px 20px; margin: 8px 0; box-sizing: border-box;border-radius: 3px;" placeholder="Masukan Nama">
                <br>
                <input id="nim1" type="text" name="nim1" style="background-color: white;color: #3CBC8D;border: none;width: 90%;padding: 12px 20px; margin: 8px 0; box-sizing: border-box;border-radius: 3px;" placeholder="Masukan NIM">
                <br>
                <input type="submit" name="submit" value="Tambah" style="  background-color: #4CAF50; border: none; color: white; padding: 16px 32px; text-decoration: none; margin: 4px 2px; cursor: pointer; width: 90%">
            </form>
    </main><br>
    <footer style="text-align:center;background-color: #33ffcc;">&copy; Copyright Kelompok 32 Praktikum RSBK 2019</footer>
  </body>
</html>
