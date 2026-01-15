// Esperar a que el DOM esté completamente cargado
document.addEventListener('DOMContentLoaded', function() {
    
    // ====================
    // NAVEGACIÓN ENTRE SECCIONES
    // ====================
    
    const navButtons = document.querySelectorAll('.nav-button');
    const contentSections = document.querySelectorAll('.content-section');
    
    // Función para cambiar de sección
    function switchSection(targetId) {
        // Remover clase active de todos los botones
        navButtons.forEach(btn => {
            btn.classList.remove('active');
        });
        
        // Añadir clase active al botón correspondiente
        document.querySelector(`[data-target="${targetId}"]`).classList.add('active');
        
        // Ocultar todas las secciones
        contentSections.forEach(section => {
            section.classList.remove('active');
        });
        
        // Mostrar la sección correspondiente
        document.getElementById(targetId).classList.add('active');
        
        // Guardar la sección activa en localStorage
        localStorage.setItem('activeSection', targetId);
    }
    
    // Añadir event listeners a los botones de navegación
    navButtons.forEach(button => {
        button.addEventListener('click', function() {
            const targetId = this.getAttribute('data-target');
            switchSection(targetId);
        });
    });
    
    // Restaurar la sección activa desde localStorage (si existe)
    const savedSection = localStorage.getItem('activeSection');
    if (savedSection && document.getElementById(savedSection)) {
        switchSection(savedSection);
    }
    
    // ====================
    // MANEJO DEL FORMULARIO DE CONTACTO
    // ====================
    
    const contactForm = document.getElementById('contactForm');
    
    if (contactForm) {
        contactForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            // Validar campos del formulario
            const name = document.getElementById('name').value.trim();
            const email = document.getElementById('email').value.trim();
            const subject = document.getElementById('subject').value.trim();
            const message = document.getElementById('message').value.trim();
            
            // Validaciones básicas
            if (!name || !email || !subject || !message) {
                alert('Por favor, completa todos los campos del formulario.');
                return;
            }
            
            if (!isValidEmail(email)) {
                alert('Por favor, introduce una dirección de email válida.');
                return;
            }
            
            // Simular envío del formulario
            // En un caso real, aquí se enviaría a un servidor
            
            // Mostrar mensaje de éxito
            showNotification('Mensaje enviado. Gracias por contactar con Javier Moles. Te responderemos pronto.');
            
            // Resetear formulario
            this.reset();
            
            // Guardar en localStorage (simulación)
            saveContactAttempt(name, email, subject);
        });
    }
    
    // Función para validar email
    function isValidEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }
    
    // Función para mostrar notificaciones
    function showNotification(message) {
        // Crear elemento de notificación
        const notification = document.createElement('div');
        notification.className = 'notification';
        notification.textContent = message;
        notification.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            background-color: #444;
            color: white;
            padding: 15px 25px;
            border-radius: 5px;
            z-index: 2000;
            box-shadow: 0 4px 12px rgba(0,0,0,0.5);
            animation: slideIn 0.3s ease-out;
        `;
        
        // Añadir al documento
        document.body.appendChild(notification);
        
        // Remover después de 5 segundos
        setTimeout(() => {
            notification.style.animation = 'slideOut 0.3s ease-out';
            setTimeout(() => {
                if (notification.parentNode) {
                    notification.parentNode.removeChild(notification);
                }
            }, 300);
        }, 5000);
        
        // Añadir estilos de animación si no existen
        if (!document.querySelector('#notification-styles')) {
            const style = document.createElement('style');
            style.id = 'notification-styles';
            style.textContent = `
                @keyframes slideIn {
                    from { transform: translateX(100%); opacity: 0; }
                    to { transform: translateX(0); opacity: 1; }
                }
                @keyframes slideOut {
                    from { transform: translateX(0); opacity: 1; }
                    to { transform: translateX(100%); opacity: 0; }
                }
            `;
            document.head.appendChild(style);
        }
    }
    
    // Función para guardar intentos de contacto (simulación)
    function saveContactAttempt(name, email, subject) {
        const attempts = JSON.parse(localStorage.getItem('contactAttempts') || '[]');
        attempts.push({
            name,
            email,
            subject,
            timestamp: new Date().toISOString()
        });
        localStorage.setItem('contactAttempts', JSON.stringify(attempts));
    }
    
    // ====================
    // EFECTOS DE TV ANTIGUA
    // ====================
    
    // Efecto de parpadeo aleatorio para simular TV vieja
    function randomFlicker() {
        const overlay = document.querySelector('.tv-overlay');
        if (overlay) {
            // Intensidad aleatoria entre 0.1 y 0.3
            const intensity = Math.random() * 0.2 + 0.1;
            overlay.style.opacity = intensity.toString();
            
            // Ocasionalmente, añadir un destello fuerte
            if (Math.random() < 0.05) {
                overlay.style.opacity = '0.5';
                setTimeout(() => {
                    overlay.style.opacity = intensity.toString();
                }, 50);
            }
        }
        
        // Frecuencia aleatoria entre 0.1 y 0.5 segundos
        setTimeout(randomFlicker, Math.random() * 400 + 100);
    }
    
    // Iniciar efecto de parpadeo después de un breve retraso
    setTimeout(randomFlicker, 1000);
    
    // ====================
    // GALERÍA INTERACTIVA
    // ====================
    
    // Añadir efecto de zoom a las imágenes de la galería
    const galleryItems = document.querySelectorAll('.gallery-item');
    
    galleryItems.forEach(item => {
        item.addEventListener('click', function() {
            // Crear modal para imagen ampliada
            const imgSrc = this.querySelector('img').src;
            const modal = document.createElement('div');
            modal.className = 'gallery-modal';
            modal.innerHTML = `
                <div class="modal-content">
                    <span class="close-modal">&times;</span>
                    <img src="${imgSrc}" alt="Imagen ampliada">
                </div>
            `;
            
            // Estilos del modal
            modal.style.cssText = `
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.9);
                display: flex;
                justify-content: center;
                align-items: center;
                z-index: 2000;
                animation: fadeIn 0.3s;
            `;
            
            // Estilos del contenido del modal
            modal.querySelector('.modal-content').style.cssText = `
                max-width: 90%;
                max-height: 90%;
                position: relative;
            `;
            
            modal.querySelector('img').style.cssText = `
                width: 100%;
                height: auto;
                border: 5px solid #555;
            `;
            
            // Estilos del botón de cerrar
            modal.querySelector('.close-modal').style.cssText = `
                position: absolute;
                top: -40px;
                right: 0;
                color: white;
                font-size: 30px;
                cursor: pointer;
            `;
            
            // Añadir al documento
            document.body.appendChild(modal);
            
            // Funcionalidad para cerrar el modal
            modal.querySelector('.close-modal').addEventListener('click', function() {
                modal.style.animation = 'fadeOut 0.3s';
                setTimeout(() => {
                    if (modal.parentNode) {
                        modal.parentNode.removeChild(modal);
                    }
                }, 300);
            });
            
            // Cerrar al hacer clic fuera de la imagen
            modal.addEventListener('click', function(e) {
                if (e.target === modal) {
                    modal.style.animation = 'fadeOut 0.3s';
                    setTimeout(() => {
                        if (modal.parentNode) {
                            modal.parentNode.removeChild(modal);
                        }
                    }, 300);
                }
            });
            
            // Añadir animación de salida si no existe
            if (!document.querySelector('#modal-styles')) {
                const style = document.createElement('style');
                style.id = 'modal-styles';
                style.textContent = `
                    @keyframes fadeOut {
                        from { opacity: 1; }
                        to { opacity: 0; }
                    }
                `;
                document.head.appendChild(style);
            }
        });
    });
    
    // ====================
    // REPRODUCTOR DE VIDEO SIMULADO
    // ====================
    
    // Si hay un contenedor de video, añadir funcionalidad
    const videoPlaceholder = document.querySelector('.film-container div');
    
    if (videoPlaceholder) {
        videoPlaceholder.addEventListener('click', function() {
            // En un caso real, aquí se iniciaría el video
            // Por ahora, mostramos un mensaje
            this.innerHTML = `
                <div style="text-align: center; padding: 50px;">
                    <i class="fas fa-play-circle" style="font-size: 4rem; margin-bottom: 20px;"></i>
                    <p>REPRODUCIENDO: "RESONANCIA ANALÓGICA"</p>
                    <p style="font-size: 1rem; color: #aaa; margin-top: 20px;">
                        (En un sitio real aquí se reproduciría el video)<br>
                        Duración: 12:00
                    </p>
                    <div style="margin-top: 30px; width: 80%; height: 10px; background: #333; margin-left: auto; margin-right: auto;">
                        <div style="width: 30%; height: 100%; background: #666;"></div>
                    </div>
                    <button id="stop-video" style="margin-top: 20px; padding: 10px 20px; background: #555; color: white; border: none; cursor: pointer;">
                        Detener reproducción
                    </button>
                </div>
            `;
            
            // Añadir funcionalidad al botón de detener
            document.getElementById('stop-video').addEventListener('click', function() {
                videoPlaceholder.innerHTML = `
                    <div style="text-align: center;">
                        <i class="fas fa-play-circle" style="font-size: 4rem; margin-bottom: 20px;"></i>
                        <p>SHORT FILM: "RESONANCIA ANALÓGICA"</p>
                        <p style="font-size: 1rem; color: #aaa;">(En un sitio real aquí se incrustaría el video)</p>
                    </div>
                `;
                
                // Volver a añadir el event listener
                videoPlaceholder.addEventListener('click', arguments.callee);
            });
        });
    }
    
    // ====================
    // INICIALIZACIÓN ADICIONAL
    // ====================
    
    // Añadir efecto de carga inicial
    window.addEventListener('load', function() {
        document.body.style.opacity = '0';
        document.body.style.transition = 'opacity 0.5s';
        
        setTimeout(() => {
            document.body.style.opacity = '1';
        }, 100);
    });
    
    // Añadir efecto de sonido al hacer clic en botones (opcional)
    navButtons.forEach(button => {
        button.addEventListener('click', function() {
            // En un caso real, aquí se podría añadir un sonido de click
            this.style.transform = 'scale(0.95)';
            setTimeout(() => {
                this.style.transform = 'scale(1)';
            }, 150);
        });
    });
});
