setTimeout(() => {
  document.getElementById("splash").style.display = "none";
  document.getElementById("main").classList.remove("hidden");
}, 2000);

function calculate() {
  let gram = Number(document.getElementById("gram").value);
  let size = Number(document.getElementById("size").value);
  let rate = Number(document.getElementById("rate").value);

  if (!gram || !size || !rate) {
    alert("Please enter all values");
    return;
  }

  let bagWeight = (gram * size) / 10;
  let price = (rate / 1000) * bagWeight;
  let netPrice = price + 2.3;

  document.getElementById("result").innerText =
    `Bag Weight: ${bagWeight.toFixed(2)} | Net Price: ₹${netPrice.toFixed(2)}`;
}

function share() {
  let text = document.getElementById("result").innerText;
  let url = `https://wa.me/919399988455?text=${encodeURIComponent(text)}`;
  window.open(url, "_blank");
}
