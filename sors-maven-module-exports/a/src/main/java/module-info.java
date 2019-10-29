module a {
  opens a to b;
  exports a to b;
  // exports x to b; // "package is empty or does not exist: x"
}
