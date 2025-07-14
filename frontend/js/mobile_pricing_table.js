let currentPlan = 0;

function updatePlan(index) {
currentPlan = index;

document.querySelectorAll('#mobilePlanSelector .tier').forEach((t, i) => {
    t.classList.toggle('active', i == index);
});

document.querySelectorAll('.pricing-options').forEach(optGroup => {
    const options = optGroup.querySelectorAll('.option');

    options.forEach((opt, i) => {
    opt.classList.remove('visible', 'exit-left', 'exit-right');
    if (i === index) {
        opt.classList.add('visible');
    } else if (i < index) {
        opt.classList.add('exit-left');
    } else {
        opt.classList.add('exit-right');
    }
    });
});
}

document.querySelectorAll('#mobilePlanSelector .tier').forEach(tier => {
tier.addEventListener('click', () => {
    const index = parseInt(tier.dataset.plan);
    if (index !== currentPlan) {
    updatePlan(index);
    }
});
});

window.addEventListener('DOMContentLoaded', () => {
updatePlan(0);
});