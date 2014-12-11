(function ($) {
    $.fn.setReadOnly = function (options) {
        var optionsLocal;
        
        if ("string" === (typeof options)) {
            optionsLocal = options;
        }
        else if ("object" === (typeof options)) {
            optionsLocal = options.switched;
            
            if (options.before) {
                options.before();
            }
        }
        
        return this.each (function (index, ele) {
            var $ele = $(ele);
           
            if (optionsLocal.toLowerCase() == 'on') {
                $ele.attr('readonly', 'readonly');
                
                $ele.css({
                    'background-color': 'gray',
                    'color': 'white'
                });
            }
            else {
                $ele.removeAttr('style').removeAttr('readonly');
                
            }
            
            if ("object" === (typeof options)) {
                if (options.after) {
                    options.after();
                }
            }
            
        });
    }

})(jQuery);


