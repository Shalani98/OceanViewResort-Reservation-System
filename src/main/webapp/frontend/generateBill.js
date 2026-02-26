// This file handles generating PDF bills
const { jsPDF } = window.jspdf;

function initGenerateBill() {
  document.querySelectorAll(".generateBillBtn").forEach((btn) => {
    btn.onclick = () => {
      const id = btn.getAttribute("data-id");
      const total = btn.getAttribute("data-total");
      const resCard = btn.parentElement;

      const doc = new jsPDF();
      doc.setFontSize(18);
      doc.text("Ocean View Resort", 105, 20, { align: "center" });
      doc.setFontSize(14);
      doc.text(`Bill for Reservation #${id}`, 105, 30, { align: "center" });
      doc.setFontSize(12);

      const details = resCard.innerText
        .replace("Generate Bill", "")
        .trim()
        .split("\n");
      details.forEach((line, index) => {
        doc.text(line, 20, 50 + index * 10);
      });

      doc.text(
        `Generated Date: ${new Date().toLocaleString()}`,
        20,
        50 + details.length * 10 + 10,
      );

      doc.save(`Reservation_${id}_Bill.pdf`);
    };
  });
}
