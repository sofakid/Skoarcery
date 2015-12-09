(require 'json)

(make-face 'skoarcery-sanity-fail)
(set-face-foreground 'skoarcery-sanity-fail "black")
(set-face-background 'skoarcery-sanity-fail "lightred")

(make-face 'skoarcery-sanity-pass)
(set-face-foreground 'skoarcery-sanity-pass "black")
(set-face-background 'skoarcery-sanity-pass "lightgreen")

(defun re-seq (regexp string group)
  "Get a list of all regexp matches in a string"
  (save-match-data
    (let ((pos 0)
          matches)
      (while (string-match regexp string pos)
        (push (match-string group string) matches)
        (setq pos (match-end 0))
	)
      matches)
    )
  )

(defun skoar-build ()
  (interactive)
  (let ((default-directory "~/Documents/GitHub/Skoarcery/"))
    (let ((result (shell-command-to-string "python build.py")))
      (with-current-buffer (get-buffer-create "*skoar build*")
	(erase-buffer)
	(insert "--- skoar build ---")
	(insert result)
	)
      )
    )

  (skoar-sanity)
  )      

(defun skoar-sanity ()
  (interactive)
  (let ((default-directory "~/Documents/GitHub/Skoarcery/"))
    (let ((result (shell-command-to-string "python sanity.py"))
	  (outbuf (get-buffer-create "*skoar tests*"))
	  )

      (with-current-buffer (get-buffer-create "*skoar sanity*")
	(erase-buffer)
	(insert "\n--- skoar sanity ---\n")
	(insert result)
	)
      
      ;; clear output buffer and turn on colours
      (with-current-buffer outbuf
	  (font-lock-mode)
	  (erase-buffer)
	  )
	  
      (dolist (jsrc (re-seq "JSON: \\(.*\\)" result 1))
	(with-current-buffer outbuf
	  (let ((json-object-type 'plist)
		(json-array-type 'list))
	    (let ((o (json-read-from-string jsrc)))

	      (setq test-name (nth 0 (re-seq ".*/testing/\\(.*\\)\\.scd" (plist-get o :test) 1)))
	      (insert (propertize test-name 'font-lock-face '(:foreground "black" :background "red")))
	      (insert "\n")

	      ;; make it red
	      (dolist (failure-line (plist-get o :failures))
		(setq failure (nth 0 (re-seq ".*test_do - \\(.*\\)" failure-line 1)))

		(insert (propertize failure 'font-lock-face '(:foreground "red" :background "black")))
		(insert "\n")
     		)
	      )
	    )
	  )	
	)

      (dolist (yay (re-seq "Sanity Tests: . OK ." result 0))
	(with-current-buffer outbuf
	  (insert (propertize yay 'font-lock-face '(:foreground "black" :background "green")))
	  (insert "\n")
	  )
	)
      
      )
    )
  )

(provide 'skoarcery)
