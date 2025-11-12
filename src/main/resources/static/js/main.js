$(function () {
  var autoComplete = [
    "Roman",
    "CSS",
    "JavaScript",
    "jQuery",
    "Kişisel Gelişim",
  ];
  $("#tags").autocomplete({
    source: autoComplete,
  });
});
$(function () {
  $(window).scroll(function () {
    if ($(window).scrollTop() > 70) {
      $("#backTopButton").fadeIn();
    } else {
      $("#backTopButton").fadeOut();
    }
  });
  $("#backTopButton").click(function () {
    $("body,html").animate({ scrollTop: 0 }, 300);
  });
});

/* Category Start */
$(document).ready(function () {
  let allBooks = [];

  $.getJSON("/api/books", function (books) {
    allBooks = books;
    displayBooks(allBooks);
  });

  function displayBooks(books) {
    let kitap_card = "";
    $.each(books, function (i, book) {
      kitap_card += `
        <div class="col-lg-3 col-md-4 col-sm-6 mb-4 d-flex">
                <div class="card h-100 w-100">
                    <a class="img-container">
                        <img src="${book.picture}" class="card-img-top img-fluid" alt="${book.title}">
                    </a>
                    <div class="card-body text-center">
                        <h6 class="card-title"><a href="/books/${book.id}">${book.title}</a></h6>
                        <p class="card-text">${book.author}</p>
                        <p  class="card-text">${book.price} ₺</p>

                        <a href="#" class="btn btn-primary w-100 mt-2">
                            <span class="fas fa-shopping-basket"></span> Sepet
                        </a>
                    </div>
                </div>
            </div>
        `;
    });
    $("#kitap_listesi").html(kitap_card);
  }

  let categories = [];
  $.getJSON("/api/categorys", function (data) {
    categories = data;
    categories.forEach((cat) => {
      $("#categoryFilter").append(
        `<option value="${cat.id}">${cat.name}</option>`
      );
    });
  });

  $("#searchInput, #categoryFilter").on("input change", function () {
    const searchTerm = $("#searchInput").val().toLowerCase();
    const selectedCategory = $("#categoryFilter").val();

    const filteredBooks = allBooks.filter((book) => {
      const matchesSearch =
        (book.title && book.title.toLowerCase().includes(searchTerm)) ||
        (book.author && book.author.toLowerCase().includes(searchTerm));
      const matchesCategory =
        selectedCategory === "" || book.categoryId == selectedCategory;
      return matchesSearch && matchesCategory;
    });
    displayBooks(filteredBooks);
  });
});
/* Category Finish */
/* Book Operations Start */
document.addEventListener("DOMContentLoaded", function () {
  const kitapModal = document.getElementById("kitapModal");
  const previewFoto = document.getElementById("previewFoto");
  const fotoUrl = document.getElementById("fotoUrl");

  kitapModal.addEventListener("show.bs.modal", function (event) {
    const btn = event.relatedTarget;

    document.getElementById("kitapId").value = btn.dataset.id || "";
    document.getElementById("kitapAdi").value = btn.dataset.adi || "";
    document.getElementById("yazar").value = btn.dataset.yazar || "";
    document.getElementById("fiyat").value = btn.dataset.fiyat || "";
    document.getElementById("stok").value = btn.dataset.stok || "";
    document.getElementById("fotoUrl").value = btn.dataset.foto || "";

    // kategori seçimi
    const kategoriSelect = document.getElementById("kategori");
    Array.from(kategoriSelect.options).forEach(
      (opt) => (opt.selected = opt.value === btn.dataset.kategori)
    );

    // db’den gelen foto URL’sini göster
    if (btn.dataset.foto) {
      previewFoto.src = btn.dataset.foto;
      previewFoto.classList.remove("d-none");
    } else {
      previewFoto.classList.add("d-none");
    }
  });

  // URL değişince önizleme güncelle
  fotoUrl.addEventListener("input", function () {
    const url = this.value.trim();
    if (url) {
      previewFoto.src = url;
      previewFoto.classList.remove("d-none");
    } else {
      previewFoto.classList.add("d-none");
    }
  });
});
/* Book Operations Finish */
/* Ana Safa Start */
$(document).ready(function () {
  $.getJSON("/api/books", function (kitaplar) {
    let kitap_card = "";
    $.each(kitaplar, function (i, book) {
      kitap_card += `
            <div class="col-lg-3 col-md-4 col-sm-6 mb-4 d-flex">
                <div class="card h-100 w-100">
                    <a class="img-container">
                        <img src="${book.picture}" class="card-img-top img-fluid" alt="${book.title}">
                    </a>
                    <div class="card-body text-center">
                        <h6 class="card-title"><a href="/books/${book.id}">${book.title}</a></h6>
                        <p class="card-text">${book.author}</p>
                        <p  class="card-text">${book.price} ₺</p>

                        <a href="#" class="btn btn-primary w-100 mt-2">
                            <span class="fas fa-shopping-basket"></span> Sepet
                        </a>
                    </div>
                </div>
            </div>
            `;
    });
    $("#kitap_listesi").html(kitap_card);
  });
});
/* Ana Sayfa Finish */