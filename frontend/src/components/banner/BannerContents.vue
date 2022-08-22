<template>
  <div class="carousel-item" v-bind:class="{ active: isActive }">
    <svg
      class="bd-placeholder-img"
      width="100%"
      height="100%"
      xmlns="http://www.w3.org/2000/svg"
      aria-hidden="true"
      preserveAspectRatio="xMidYMid slice"
      focusable="false"
    >
      <rect width="100%" height="100%" fill="#777" />
    </svg>

    <div class="container">
      <div class="carousel-caption">
        <h1>Another example headline.</h1>
        <p>
          Some representative placeholder content for the second slide of the
          carousel.
        </p>
        <p><a class="btn btn-lg btn-primary" href="#">Learn more</a></p>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    isActive: Boolean,
  },
};
</script>
