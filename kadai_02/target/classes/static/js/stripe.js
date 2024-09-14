 const stripe = Stripe('pk_test_51PtkMwFEempB00yYFKKXF6FraN5hXOv7odO7emPKUgvx8WK40vbODnzH9qly4gnxaYMmX27oZ9syF40QRFIkoWtk00Qwi4HLLL');
 const paymentButton = document.querySelector('#paymentButton');
 
 paymentButton.addEventListener('click', () => {
   stripe.redirectToCheckout({
     sessionId: sessionId
   })
 });