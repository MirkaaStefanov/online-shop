function toggleColorInput() {
    var productTypeSelect = document.getElementById('productType');
    var colorField = document.getElementById('colorField');
    if (productTypeSelect.value === 'food') {
        colorField.style.display = 'none';
    } else {
        colorField.style.display = 'block';
    }
}

toggleColorInput();