const btn = document.getElementById('menu-btn')
const menu = document.getElementById('menu')

const btn_usuario = document.getElementById('menu-usuario-btn')
const menu_usuario = document.getElementById('menu-usuario')

btn.addEventListener('click', navToggle)

function navToggle() {
  btn.classList.toggle('open')
  menu.classList.toggle('flex')
  menu.classList.toggle('hidden')
}

btn_usuario.addEventListener('click', navToggleUsuario)

function navToggleUsuario() {
  menu_usuario.classList.toggle('flex')
  menu_usuario.classList.toggle('hidden')
}