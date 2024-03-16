const sliderList = document.querySelectorAll('.slider');
window.addEventListener('scroll', function(e){
  const navTop = document.querySelector('.nav-top');
  if(window.scrollY > 0){
    navTop.classList.add('scrollbar');
  }else{
    navTop.classList.remove('scrollbar');
  }
})
sliderList.forEach(function(slider){
    let active = 0;
    slider.addEventListener('click',function(e){
       const sliderWrapper = e.currentTarget.querySelector('.slider-wrapper');
       const sliderItem = sliderWrapper.querySelectorAll('.slider-item');
       const lengthItemInSlider = Math.floor(e.currentTarget.querySelector('.slider-content').offsetWidth / sliderItem[0].offsetWidth);
       if(e.target.closest('.btn-prev') && active != 0){
         active = active - 1;
         console.log(active); 
         if(active <= 0){
          slider.querySelector('.btn-prev').style.display = 'none';
        }
        if(active <= sliderItem.length - 1){
          slider.querySelector('.btn-next').style.display = 'block';
        }
         if(sliderItem[active]){
            const checkLeft = sliderItem[active].offsetLeft+'px';
            sliderWrapper.style.transform = 'translateX(-'+checkLeft+')';
          }
       }else if(e.target.closest('.btn-next') && active < sliderItem.length - lengthItemInSlider){
         active += 1;
         if(active > 0){
           slider.querySelector('.btn-prev').style.display = 'block';
         }
         if(active >= sliderItem.length - lengthItemInSlider){
          slider.querySelector('.btn-next').style.display = 'none';
         }
         if(sliderItem[active]){
             const checkLeft = -sliderItem[active].offsetLeft+'px';
             sliderWrapper.style.transform = 'translateX('+checkLeft+')';
         }
       }
    })
})