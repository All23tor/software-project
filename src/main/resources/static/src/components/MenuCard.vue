<template>
  <div class="menu-card">
    <h1>🍔 Welcome to QuickBite!</h1>
    <p class="tagline">Delicious food, delivered fast.</p>

    <div class="highlight-image">
      <img :src="selected.img" :alt="selected.name" />
    </div>

    <CategoryCarousel
        :categories="categories"
        :selected="selected"
        @select="selectCategory"
    />

    <button @click="startOrder">Start Order 🍽️</button>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import CategoryCarousel from './CategoryCarousel.vue'
import { categories } from '../data/categories.js'

const selected = ref(categories[0])

function selectCategory(cat) {
  selected.value = cat
}

function startOrder() {
  alert("Redirecting to the menu...")
}
</script>

<style scoped>
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

.tagline {
  color: #444;
  font-size: 1.4rem;
  margin-bottom: 2rem;
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

button {
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

button:hover {
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
</style>