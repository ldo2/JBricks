# -*- mode: makefile -*-

.PHONY : clean, veryclean

MAIN       = main

TEXFILES   := $(wildcard *.tex) $(wildcard ../*.tex) $(wildcard body/*.tex)
IMGFILES   := $(wildcard images/*)
PRGFILES   := $(wildcard examples/*)

FILES      = Makefile $(TEXFILES) $(IMGFILES) $(PRGFILES)

TEX        = latex
DVIPS      = dvips -K -O -2mm,86mm -x815 -o main.ps
XDVI       = xdvi -expert -paper 17.5x24.75cm -s 6 
GV         = gv 
GS	   = gs -q -dNOPAUSE -sDEVICE=ljet4 -sOutputFile=\|lpr - 
LATEX2HTML = latex2html
LYNX       = lynx
MKBOOK     = mkbook
MPAGE      = mpage -2g -m0 -M0 -o

.SUFFIXES: .tex .dvi .ps .html

.tex.dvi:
	$(TEX) $<

.tex.html:
	$(LATEX2HTML) $<

.dvi.ps:
	$(DVIPS) $<

view: $(MAIN).dvi
	$(XDVI) $<

main.dvi: $(FILES)
	$(TEX) $(MAIN).tex
	$(TEX) $(MAIN).tex

# ���������� Postscript ����
gv:   $(MAIN).ps
	$(GV) $<

# ���������� ������
print: $(MAIN).ps
	cat $< | $(MKBOOK) 0 0 | $(MPAGE) >page1.ps
	cat $< | $(MKBOOK) 1 0 | $(MPAGE) >page2.ps

# ���������� 1-� ������� ������� �� LJ 4L/5L/6L/1100/1200
lj1:	print
	cat page1.ps | $(GS)

# ���������� 1-� ������� ������� �� LJ 4L/5L/6L/1100/1200
lj2:	print
	cat page2.ps | $(GS)

# ������� ������ �����
clean:
	rm -f $(MAIN).aux $(MAIN).toc $(MAIN).log $(MAIN).idx $(MAIN).ilg \
	$(MAIN).ind $(MAIN).idx $(MAIN).dvi $(MAIN).ps page[12].ps 

# ������� ����� ��� ������ �����
veryclean:
	make clean
	rm -fr $(MAIN)

# �������� �������������� ������ ������
html:   $(MAIN).dvi
	$(LATEX2HTML) $(MAIN).tex

