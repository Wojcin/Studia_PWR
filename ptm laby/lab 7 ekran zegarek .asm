TIME  EQU 50				;MS 
LOAD  EQU (65536 - TIME*1000) 		;LICZBA CYKLI
SEC	  EQU 30H
MIN	  EQU 31H
HOUR  EQU 32H
COUNT EQU 33H 
SEC_UPDATE EQU 0

LCD_STATUS 	EQU 0FF2EH
LCD_CMD_WR	EQU 0FF2CH 
LCD_DANE_WR EQU 0FF2DH 

LCD_INICJ 	EQU 038H
LCD_CLR 	EQU 001H
LCD_HOME 	EQU 080H
LCD_ON 		EQU	00EH	

ORG 0	   
SJMP START
;-----------------------------------------------------------------------------------
; Procedura przerwania wywo�ywana co 1/20 SEKUNDY przez timer.
; Zwi�ksza licznik sekund. Kiedy zostaje przekroczony zeruje go i zwi�ksza minuty.
;Odpowiednio dla minut i godzin.
;-----------------------------------------------------------------------------------
ORG 0BH 					;ADRES PRZERWANIA
TIMER_PRZERW:
	PUSH PSW 				; Kopia flag rejestru
	PUSH ACC 				; Koia akumulatora
	MOV TH0, #HIGH(LOAD) 		; za�adowanie bit�w z liczb� cykli
	MOV TL0, #LOW(LOAD) 		; jak wy�ej
	DJNZ COUNT, KONIEC 		; skok do KONIEC je�eli COUNT > 0
	MOV COUNT, #20 		; Je�eli COUNT = 0 (min�a sekunda), ponownie ustaw 20
	SETB SEC_UPDATE 			; ustawienie flagi zmian
	
	INC SEC 				; Inkrementacja sekund
	MOV A, SEC 				; przeniesienie sekund do akumulatora
	CJNE A, #60, KONIEC 	; by sprawdzi� czy wynosz� 60, je�eli nie to skok do KONIEC
	
	MOV SEC, #0 				; Wyzerowanie sekund
	INC MIN 				; Inkrementacja liczby minut
	MOV A, MIN 				; Analogicznie do sekund - kopia do akumulatora
	CJNE A, #60, KONIEC 			; Je�eli limit nie osi�gni�ty to skok do KONIEC
	
	MOV MIN, #0 				; Wyzerowanie minut
	INC HOUR 				; Inkrementacja godzin
	MOV A, HOUR 			
	CJNE A, #24, KONIEC 	
	MOV HOUR, #0 			

KONIEC:
	POP ACC 				; Przywr�cenie pierwotnego akumulatora
	POP PSW 				; Przywr�cenie pierwotnego rejestru flag
	RETI 					; Wyj�cie z procedury przerwania

;-----------------------------------------------------------------------------------
; G��wny fragment programu
;-----------------------------------------------------------------------------------
START:
 		MOV SEC, #55
		MOV MIN, #59
		MOV HOUR, #2

		LCALL	INICJUJ_TIMER
		
		MOV		A, #LCD_INICJ
		CALL	LCD_KOMENDA

		MOV		A, #LCD_CLR			  	
		CALL	LCD_KOMENDA

		MOV		A, #LCD_ON
		CALL	LCD_KOMENDA

		MOV		A, #LCD_HOME
		CALL	LCD_KOMENDA


PETLA_GLOWNA:
		JNB		SEC_UPDATE,PETLA_GLOWNA
		CLR		SEC_UPDATE
		LCALL  DISPLAY_TIME 	;Tutaj b�dzie si� znajdowa� wywo�anie funkcji wy�wietlenia czasu	
	
		SJMP	PETLA_GLOWNA	
;-----------------------------------------------------------------------------------
; Inicjalizacja timera korzystaj�cego z przerwa�, odmierza 1 sekund�
;-----------------------------------------------------------------------------------
INICJUJ_TIMER:
	MOV HOUR,#0 			; Zerowanie licznika godzin
	MOV MIN, #0 				; Zerowanie licznika minut
	MOV SEC, #0 				; Zerowanie licznika sekund
	CLR TR0 				; Zatrzymanie timera
	ANL TMOD, #11110000B		; Wyzerowanie rejestru steruj�cego timera
	ORL TMOD, #00000001B 		; Ustawienie pierwotnego trybu timera
	MOV TH0, #HIGH(LOAD); Za�adowanie starszych bit�w zmiennej z liczb� cykli(stop)
	MOV TL0, #LOW(LOAD) ; Za�adowanie starszych bit�w zmiennej z liczb� cykli(stop)
	MOV COUNT, #20 	; Ustawienie licznika czasu na 20 cykli - ka�dy po 1/20 sekundy
	CLR TF0 				; Czyszczenie bitu przepe�nienia
	SETB ET0 				; Ustawienie obs�ugi przerwania
	SETB EA 				; Ustawienie obs�ugi przerwania
	SETB TR0 				; Uruchomienie Timera
	RET 					; Wyj�cie	

LCD_KOMENDA:
	MOV DPTR, #LCD_STATUS
	PUSH ACC
PETLA:
	MOVX A, @DPTR
	JB ACC.7, PETLA
	POP ACC
	MOV DPTR, #LCD_CMD_WR
	MOVX @DPTR, A
 	RET
	
LCD_DANE:
	MOV DPTR, #LCD_STATUS
	PUSH ACC
PETLA1:
	MOVX A, @DPTR
	JB ACC.7, PETLA1
	POP ACC
	MOV DPTR, #LCD_DANE_WR
	MOVX @DPTR, A
 	RET

;----------------------------------
; A - powinno zawiera� dziesi�tn� liczb� b�d�c� godzin�, minutami, sekundami
WR_DWIE_CYFRY:
    MOV B, #10
    DIV AB     	;dzielenie godziny, po operacji w A dziesiatki, w B pojedyncze 
    ADD A, #'0' ;poprzed dodanie kodu ASCII zera obliczymy kod cyfry
    LCALL WRITE_CHAR ;wy�wietlenie cyfry dziesi�tnej
    MOV A, B   	;przenosimy reszt� z dzielenia do A - pojedyncze wartosci
    ADD A, #'0' ;obliczamy kod znaku
    LCALL WRITE_CHAR ;wypisujemy pojedyncza wartosc
    RET
RET
	
	
END
