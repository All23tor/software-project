<template>
  <div class="app">
    <div class="menu-card">
      <h1>🍔 Welcome to QuickBite!</h1>
      <p class="tagline">Delicious food, delivered fast.</p>

      <div class="highlight-image">
        <img :src="selected.img" :alt="selected.name"/>
      </div>

      <!-- Carrusel de categorías -->
      <div class="category-carousel-wrapper">
        <div class="category-carousel" :style="carouselStyle">
          <div
              class="category"
              v-for="(cat, i) in repeatedCategories"
              :key="i"
              :class="{ active: cat.name === selected.name }"
              @click="selected = cat"
          >
            <span class="icon">{{ cat.icon }}</span>
            <span class="name">{{ cat.name }}</span>
          </div>
        </div>
      </div>
      <button @click="startOrder">Start Order 🍽️</button>
    </div>
  </div>
</template>

<script setup>
import ref from 'vue'

const categories = [
  {
    name: "Burgers",
    icon: "🍔",
    img: "https://thestayathomechef.com/wp-content/uploads/2013/10/The-Perfect-Burger-H1-1200x720.jpg ",
  },
  {
    name: "Sushi",
    icon: "🍣",
    img: "https://int.japanesetaste.com/cdn/shop/articles/how-to-make-makizushi-sushi-rolls-japanese-taste.jpg",
  },
  {
    name: "Pizza",
    icon: "🍕",
    img: "https://hips.hearstapps.com/hmg-prod/images/classic-cheese-pizza-recipe-2-64429a0cb408b.jpg",
  },
  {
    name: "Tacos",
    icon: "🌮",
    img: "https://www.thefoodinmybeard.com/content/taco/whitepeople/wpt10.jpg"},
  {
    name: "Pasta",
    icon: "🍝",
    img: "https://s.lightorangebean.com/media/20240914160809/Spicy-Penne-Pasta_-done.png",
  },
  {
    name: "Salads",
    icon: "🥗",
    img: "https://www.tasteofhome.com/wp-content/uploads/2025/02/Favorite-Mediterranean-Salad_EXPS_TOHcom25_41556_MD_P2_02_05_1b.jpg",
  },
  {
    name: "Desserts",
    icon: "🍰",
    img: "https://cdn.hswstatic.com/gif/desserts-update.jpg",
  },
  {
    name: "Drinks",
    icon: "🥤",
    img: "https://www.beanilla.com/wp/wp-content/uploads/2022/06/RefreshingDrinks.jpg",
  },
];

// Estado reactivo para la categoría seleccionada
const selected = ref(categories[0]);

// Duplicamos para efecto carrusel
const repeatedCategories = [...categories, ...categories];

// Control de duración del loop
const animationDuration = `${categories.length * 3}s`;
const carouselStyle = {
  animation: `scroll-left ${animationDuration} linear infinite`,
};

const featured = [
  {name: "Cheeseburger", img: "https://i.imgur.com/7D7I6dI.png"},
  {name: "Sushi", img: "https://i.imgur.com/sP4Y5ZK.png"},
  {name: "Pizza", img: "https://i.imgur.com/TlW5BzI.png"},
];

function startOrder() {
  alert("Redirecting to the menu...");
}
</script>

<style>
body {
  margin: 0;
  font-family: 'Segoe UI', sans-serif;
  background: linear-gradient(to bottom right, #ffe7e7, #fff5d6);
}

.app {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  padding: 2rem;
}

.menu-card {
  background: white;
  padding: 3rem 2.5rem;
  border-radius: 2rem;
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.1);
  text-align: center;
  max-width: 550px;
  width: 100%;
  animation: fadeIn 0.5s ease-out;
}

.menu-card h1 {
  font-size: 2.8rem;
  color: #e05656;
  margin-bottom: 0.8rem;
}

.menu-card .tagline {
  color: #444;
  font-size: 1.4rem;
  margin-bottom: 2rem;
}

.category-carousel::-webkit-scrollbar {
  display: none;
}

.category .icon {
  font-size: 1.5rem;
}

.category:hover {
  background: #e05656;
  color: white;
  transform: translateY(-2px);
}

.food img {
  width: 90px;
  height: 90px;
  object-fit: contain;
  border-radius: 0.75rem;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
  margin-bottom: 0.6rem;
  transition: transform 0.2s ease;
}

.food img:hover {
  transform: scale(1.1);
}

.menu-card button {
  background: #e05656;
  color: white;
  padding: 1rem 2.5rem;
  border: none;
  border-radius: 999px;
  font-size: 1.2rem;
  font-weight: bold;
  cursor: pointer;
  transition: background 0.3s ease, transform 0.2s ease;
}

.menu-card button:hover {
  background: #c24545;
  transform: translateY(-3px);
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Animación de desplazamiento */
@keyframes scroll-left {
  from {
    transform: translateX(0%);
  }
  to {
    transform: translateX(-50%);
  }
}

.category {
  flex: 0 0 auto;
  background: #ffeaea;
  padding: 0.75rem 1.5rem;
  border-radius: 999px;
  font-weight: 600;
  font-size: 1.2rem;
  color: #e05656;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.2s ease;
  white-space: nowrap;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 0.6rem;
}

.highlight-image {
  width: 100%;
  margin-bottom: 1.5rem;
}

.highlight-image img {
  width: 100%;
  max-height: 200px;
  object-fit: cover;
  border-radius: 1rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

/* categoría activa */
.category.active {
  background: #e05656 !important;
  color: white !important;
  transform: scale(1.05);
}

/* resto ya definido antes */
.category-carousel-wrapper {
  overflow: hidden;
  margin-bottom: 2rem;
  padding-bottom: 1rem;
}

.category-carousel {
  display: inline-flex;
  gap: 1rem;
  white-space: nowrap;
}

@keyframes scroll-left {
  from {
    transform: translateX(0%);
  }
  to {
    transform: translateX(-50%);
  }
}
</style>