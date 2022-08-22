<template>
  <button class="{{btnClass}}" @click="$router.push(link)">
    <slot>btn text</slot>
  </button>
</template>
<script>
export default {
  props: {
    btnClass: String,
    link: {
      type: String,
      required: true,
    },
  },
};
</script>
