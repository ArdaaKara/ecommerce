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
    $("#kitap_listesi2").html(kitap_card);
  }

  let categories = [];
  $.getJSON("/api/category", function (data) {
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
    document.getElementById("silIdInput").value = btn.dataset.id || "";
    
    const kategoriSelect = document.getElementById("kategori");
    Array.from(kategoriSelect.options).forEach(
      (opt) => (opt.selected = opt.value === btn.dataset.kategori)
    );

    
    if (btn.dataset.foto) {
      previewFoto.src = btn.dataset.foto;
      previewFoto.classList.remove("d-none");
    } else {
      previewFoto.classList.add("d-none");
    }
  });

  
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
  const kitapSayisiPerSayfa = 12; 

  function loadBooks(page = 0) {
    $.getJSON(
      "/api/booksPAGE",
      {
        page: page,
        size: kitapSayisiPerSayfa,
      },
      function (data) {
        let html = "";
        $.each(data.books, function (i, book) {
          html += `
                <div class="col-lg-3 col-md-4 col-sm-6 mb-4 d-flex">
                    <div class="card h-100 w-100 shadow-sm">
                        <a href="/books/${
                          book.id
                        }" class="text-decoration-none">
                            <img src="${book.picture || "/img/no-image.jpg"}" 
                                 class="card-img-top" style="height: 450px; object-fit: cover;" 
                                 alt="${book.title}">
                        </a>
                        <div class="card-body text-center d-flex flex-column">
                            <h6 class="card-title">
                                <a href="/books/${book.id}" class="text-dark">
                                    ${
                                      book.title.length > 35
                                        ? book.title.substring(0, 35) + "..."
                                        : book.title
                                    }
                                </a>
                            </h6>
                            <p class="text-muted small">${book.author}</p>
                            <p class="fw-bold text-primary mt-auto">${
                              book.price
                            } ₺</p>
                            <a href="#" class="btn btn-outline-primary btn-sm mt-2">Sepete Ekle</a>
                        </div>
                    </div>
                </div>`;
        });

        $("#kitap_listesi").html(html);

        buildPagination(data.currentPage, data.totalPages);
      }
    );
  }

  function buildPagination(currentPage, totalPages) {
    if (totalPages <= 1) {
      $("#pagination").remove();
      return;
    }

    let pagination = `<nav><ul class="pagination justify-content-center">`;

    pagination += `<li class="page-item ${currentPage == 0 ? "disabled" : ""}">
            <a class="page-link" href="#" data-page="${
              currentPage - 1
            }">Önceki</a></li>`;

    for (let i = 0; i < totalPages; i++) {
      pagination += `<li class="page-item ${i == currentPage ? "active" : ""}">
                <a class="page-link" href="#" data-page="${i}">${
        i + 1
      }</a></li>`;
    }

    pagination += `<li class="page-item ${
      currentPage >= totalPages - 1 ? "disabled" : ""
    }">
            <a class="page-link" href="#" data-page="${
              currentPage + 1
            }">Sonraki</a></li>
            </ul></nav>`;

    if ($("#pagination").length) {
      $("#pagination").html(pagination);
    } else {
      $("#kitap_listesi").after(
        '<div id="pagination" class="my-5">' + pagination + "</div>"
      );
    }

    $(".page-link")
      .off("click")
      .on("click", function (e) {
        e.preventDefault();
        let yeniSayfa = $(this).data("page");
        if (yeniSayfa != null && !$(this).parent().hasClass("disabled")) {
          loadBooks(yeniSayfa);
          $("html, body").animate(
            { scrollTop: $("#kitap_listesi").offset().top - 100 },
            400
          );
        }
      });
  }

  loadBooks(0);
});
/* Ana Sayfa Finish */
