document.addEventListener('DOMContentLoaded', function() {
    // Seleccionar elementos
    const categoryTabs = document.querySelectorAll('.category-tab');
    const menuCategories = document.querySelectorAll('.menu-category');
    const addButtons = document.querySelectorAll('.add-btn');
    const cartEmpty = document.querySelector('.cart-empty');
    const cartItems = document.querySelector('.cart-items');
    const cartTotal = document.querySelector('.cart-total');
    const cartTotalAmount = document.getElementById('cart-total-amount');
    const paymentMethods = document.querySelectorAll('.payment-method');
    const cardIcons = document.querySelector('.card-icons');
    
    // Cambiar categorías
    categoryTabs.forEach(tab => {
        tab.addEventListener('click', function() {
            // Quitar clase activa de todas las pestañas
            categoryTabs.forEach(t => t.classList.remove('active'));
            // Agregar clase activa a la pestaña seleccionada
            this.classList.add('active');
            
            // Ocultar todas las categorías
            menuCategories.forEach(cat => cat.classList.remove('active'));
            
            // Mostrar la categoría seleccionada
            const categoryId = this.getAttribute('data-category');
            document.getElementById(categoryId).classList.add('active');
        });
    });
    
    // Carrito de compras
    let cart = [];
    
    // Función para actualizar el carrito
    function updateCart() {
        // Limpiar el carrito
        cartItems.innerHTML = '';
        
        // Si el carrito está vacío
        if (cart.length === 0) {
            cartEmpty.style.display = 'block';
            cartItems.style.display = 'none';
            cartTotal.style.display = 'none';
            return;
        }
        
        // Mostrar elementos del carrito
        cartEmpty.style.display = 'none';
        cartItems.style.display = 'block';
        cartTotal.style.display = 'flex';
        
        // Calcular total
        let total = 0;
        
        // Agregar cada item al carrito
        cart.forEach((item, index) => {
            const itemTotal = item.price * item.quantity;
            total += itemTotal;
            
            const itemElement = document.createElement('div');
            itemElement.className = 'cart-item';
            itemElement.innerHTML = `
                <div class="item-details">
                    <div class="item-title">${item.name}</div>
                    <div class="item-price">S/ ${item.price.toFixed(2)}</div>
                </div>
                <div class="item-quantity">
                    <button class="quantity-btn decrease" data-index="${index}">-</button>
                    <span>${item.quantity}</span>
                    <button class="quantity-btn increase" data-index="${index}">+</button>
                </div>
                <button class="remove-btn" data-index="${index}">×</button>
            `;
            cartItems.appendChild(itemElement);
        });
        
        // Actualizar total
        cartTotalAmount.textContent = `S/ ${total.toFixed(2)}`;
        
        // Agregar eventos a los botones de cantidad y eliminar
        document.querySelectorAll('.decrease').forEach(btn => {
            btn.addEventListener('click', function() {
                const index = parseInt(this.getAttribute('data-index'));
                if (cart[index].quantity > 1) {
                    cart[index].quantity--;
                    updateCart();
                }
            });
        });
        
        document.querySelectorAll('.increase').forEach(btn => {
            btn.addEventListener('click', function() {
                const index = parseInt(this.getAttribute('data-index'));
                cart[index].quantity++;
                updateCart();
            });
        });
        
        document.querySelectorAll('.remove-btn').forEach(btn => {
            btn.addEventListener('click', function() {
                const index = parseInt(this.getAttribute('data-index'));
                cart.splice(index, 1);
                updateCart();
            });
        });
    }
    
    // Agregar productos al carrito
    addButtons.forEach(button => {
        button.addEventListener('click', function() {
            const name = this.getAttribute('data-name');
            const price = parseFloat(this.getAttribute('data-price'));
            
            // Verificar si el producto ya está en el carrito
            const existingItem = cart.find(item => item.name === name);
            
            if (existingItem) {
                existingItem.quantity++;
            } else {
                cart.push({
                    name: name,
                    price: price,
                    quantity: 1
                });
            }
            
            updateCart();
        });
    });
    
    // Cambiar método de pago
    paymentMethods.forEach(method => {
        method.addEventListener('click', function() {
            // Quitar clase activa de todos los métodos
            paymentMethods.forEach(m => m.classList.remove('active'));
            // Agregar clase activa al método seleccionado
            this.classList.add('active');
            
            // Mostrar iconos de tarjetas si se selecciona tarjeta
            if (this.querySelector('#tarjeta')) {
                cardIcons.style.display = 'flex';
            } else {
                cardIcons.style.display = 'none';
            }
            
            // Marcar el radio button
            this.querySelector('input').checked = true;
        });
    });
});