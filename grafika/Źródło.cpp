/*************************************************************************************/

//Jajko 3D
//Zmiana wygladu przez klawisze 1,2,3.
//Autor: Przemyslaw Wojcinowicz
//Indeks 225943

/*************************************************************************************/

#include <windows.h>
#include <gl/gl.h>
#include <gl/glut.h>
#include <math.h>
#include <time.h>


typedef float point3[3];
typedef float point2[2];


int N = 100;
int model = 2; // przechowwanie ktory model bedzie wyswietlany po wlaczeniu programu
static GLfloat theta[] = { 0.0, 0.0, 0.0 };
point3 **colorTab;

/*************************************************************************************/

// Funkcja rysuj�ca osie uk�adu wsp�rz�dnych



void Axes()
{

	point3  x_min = { -5.0, 0.0, 0.0 };
	point3  x_max = { 5.0, 0.0, 0.0 };
	// pocz�tek i koniec obrazu osi x

	point3  y_min = { 0.0, -5.0, 0.0 };
	point3  y_max = { 0.0, 5.0, 0.0 };
	// pocz�tek i koniec obrazu osi y

	point3  z_min = { 0.0, 0.0, -5.0 };
	point3  z_max = { 0.0, 0.0, 5.0 };
	//  pocz�tek i koniec obrazu osi z

	glColor3f(1.0f, 0.0f, 0.0f);  // kolor rysowania osi - czerwony
	glBegin(GL_LINES); // rysowanie osi x

	glVertex3fv(x_min);
	glVertex3fv(x_max);

	glEnd();

	glColor3f(0.0f, 1.0f, 0.0f);  // kolor rysowania - zielony
	glBegin(GL_LINES);  // rysowanie osi y

	glVertex3fv(y_min);
	glVertex3fv(y_max);

	glEnd();

	glColor3f(0.0f, 0.0f, 1.0f);  // kolor rysowania - niebieski
	glBegin(GL_LINES); // rysowanie osi z

	glVertex3fv(z_min);
	glVertex3fv(z_max);


	glEnd();

}

/*************************************************************************************/

// Funkcja okre�laj�ca co ma by� rysowane (zawsze wywo�ywana gdy trzeba
// przerysowa� scen�)


void drawEgg()
{
	point3 **xyzTab = new point3*[N];
	point2 **uvTab = new point2*[N];
	for (int i = 0; i < N; i++)
	{
		xyzTab[i] = new point3[N];
		uvTab[i] = new point2[N];
	}

	for (int i = 0; i < N; i++)
	{
		for (int j = 0; j < N; j++)
		{
			uvTab[i][j][0] = i / (float)(N - 1);
			uvTab[i][j][1] = j / (float)(N - 1);
		}
	}

	float u;
	float v;

	for (int i = 0; i < N; i++)
	{
		for (int j = 0; j < N; j++)
		{
			u = uvTab[i][j][0];
			v = uvTab[i][j][1];
			// obliczanie wspolrzednych
			xyzTab[i][j][0] =
				(-90 * pow(u, 5)
					+ 225 * pow(u, 4)
					- 270 * pow(u, 3)
					+ 180 * pow(u, 2)
					- 45 * u)
				*
				(cos(3.14*v))
				;
			xyzTab[i][j][1] =
				160 * pow(u, 4)
				- 320 * pow(u, 3)
				+ 160 * pow(u, 2)
				;
			xyzTab[i][j][2] =
				(-90 * pow(u, 5)
					+ 225 * pow(u, 4)
					- 270 * pow(u, 3)
					+ 180 * pow(u, 2)
					- 45 * u)
				*
				(sin(3.14*v))
				;
		}
	}



	switch (model)
	{
	case 1: // powstanie jajka z punktow 
		glBegin(GL_POINTS);
		for (int i = 0; i < N; i++)
		{
			glColor3f(4.2, 1.0, 1.2);
			for (int j = 0; j < N; j++)
			{
				glVertex3fv(xyzTab[i][j]);
			}
		}
		glEnd();
		break;

	case 2:
		glBegin(GL_LINES); // powstanie jajka z siatki
		for (int i = 0; i < N - 1; i++)
		{
			glColor3f(4.2, 1.0, 1.2);
			for (int j = 0; j < N - 1; j++)
			{
				glVertex3fv(xyzTab[i][j]);
				glVertex3fv(xyzTab[i][j + 1]);

				glVertex3fv(xyzTab[i][j]);
				glVertex3fv(xyzTab[i + 1][j + 1]);

				glVertex3fv(xyzTab[i][j]);
				glVertex3fv(xyzTab[i + 1][j]);
			}
		}
		glEnd();
		break;

	case 3:
		glBegin(GL_TRIANGLES); // powstanie jajka z trojkatow
		for (int i = 0; i < N - 1; i++)
		{
			for (int j = 0; j < N - 1; j++)
			{
				//
				glColor3fv(colorTab[i][j]);
				glVertex3fv(xyzTab[i][j]);

				glColor3fv(colorTab[i + 1][j]);
				glVertex3fv(xyzTab[i + 1][j]);

				glColor3fv(colorTab[i + 1][j + 1]);
				glVertex3fv(xyzTab[i + 1][j + 1]);


				//
				glColor3fv(colorTab[i + 1][j + 1]);
				glVertex3fv(xyzTab[i + 1][j + 1]);

				glColor3fv(colorTab[i][j]);
				glVertex3fv(xyzTab[i][j]);

				glColor3fv(colorTab[i][j + 1]);
				glVertex3fv(xyzTab[i][j + 1]);

			}
		}
		glEnd();
		break;

	default:
		break;
	}


}

void spinEgg()
{
	float speed = 0.5;
	theta[0] -= speed;
	if (theta[0] > 360.0) theta[0] -= 360.0;

	theta[1] -= speed;
	if (theta[1] > 360.0) theta[1] -= 360.0;

	theta[2] -= speed;
	if (theta[2] > 360.0) theta[2] -= 360.0;

	glutPostRedisplay(); //od�wie�enie zawarto�ci aktualnego okna
}

void generateRandomColorTab()
{
	colorTab = new point3*[N];
	for (int i = 0; i < N; i++)
	{
		colorTab[i] = new point3[N];

		for (int j = 0; j < N - 1; j++)
		{
			colorTab[i][j][0] = (float)(rand() % 1000) / 1000;
			colorTab[i][j][1] = (float)(rand() % 1000) / 1000;
			colorTab[i][j][2] = (float)(rand() % 1000) / 1000;
		}

		colorTab[i][N - 1][0] = colorTab[i][0][0];
		colorTab[i][N - 1][1] = colorTab[i][0][1];
		colorTab[i][N - 1][2] = colorTab[i][0][2];


	}
	for (int i = 0; i < N; i++)
	{
		colorTab[i][N - 1][0] = 1.00;//colorTab[0][i][0];
		colorTab[i][N - 1][1] = 0;//colorTab[0][i][1];
		colorTab[i][N - 1][2] = 0;//colorTab[0][i][2];
	}
}

void RenderScene(void)
{

	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	// Czyszczenie okna aktualnym kolorem czyszcz�cym

	glLoadIdentity();
	// Czyszczenie macierzy bie��cej

	Axes();
	// Narysowanie osi przy pomocy funkcji zdefiniowanej wy�ej


	// Obracanie jajka

	glRotatef(theta[0], 1.0, 0.0, 0.0);
	glRotatef(theta[1], 0.0, 1.0, 0.0);
	glRotatef(theta[2], 0.0, 0.0, 1.0);

	//glRotated(30, 1, 0, 0);

	glTranslated(0, -4, 0);

	drawEgg();

	glFlush();
	// Przekazanie polece� rysuj�cych do wykonania



	glutSwapBuffers();
	//

}


// Funkcja ma za zadanie utrzymanie sta�ych proporcji rysowanych
// w przypadku zmiany rozmiar�w okna.
// Parametry vertical i horizontal (wysoko�� i szeroko�� okna) s�
// przekazywane do funkcji za ka�dym razem gdy zmieni si� rozmiar okna.



void ChangeSize(GLsizei horizontal, GLsizei vertical)
{

	GLfloat AspectRatio;
	// Deklaracja zmiennej AspectRatio  okre�laj�cej proporcj�
	// wymiar�w okna 

	if (vertical == 0)  // Zabezpieczenie przed dzieleniem przez 0

		vertical = 1;

	glViewport(0, 0, horizontal, vertical);
	// Ustawienie wielko�ciokna okna widoku (viewport)
	// W tym przypadku od (0,0) do (horizontal, vertical)  

	glMatrixMode(GL_PROJECTION);
	// Prze��czenie macierzy bie��cej na macierz projekcji 

	glLoadIdentity();
	// Czyszcznie macierzy bie��cej            

	AspectRatio = (GLfloat)horizontal / (GLfloat)vertical;

	// glOrtho - okresenie przestrzeni ograniczajacej okno           

	if (horizontal <= vertical)

		glOrtho(-7.5, 7.5, -7.5 / AspectRatio, 7.5 / AspectRatio, 10.0, -10.0);

	else

		glOrtho(-7.5*AspectRatio, 7.5*AspectRatio, -7.5, 7.5, 10.0, -10.0);

	glMatrixMode(GL_MODELVIEW);
	// Prze��czenie macierzy bie��cej na macierz widoku modelu                                    

	glLoadIdentity();
	// Czyszcenie macierzy bie��cej

}

void keys(unsigned char key, int x, int y)
{
	if (key == 'p' || key == '1') model = 1;
	if (key == 'w' || key == '2') model = 2;
	if (key == 's' || key == '3') model = 3;

	RenderScene(); // przerysowanie obrazu sceny
}

/*************************************************************************************/

// G��wny punkt wej�cia programu. Program dzia�a w trybie konsoli



void main(void)
{
	srand(time(NULL));
	generateRandomColorTab();

	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);

	glutInitWindowSize(600, 600);

	glutCreateWindow("Jajko");

	glutDisplayFunc(RenderScene);
	// Okre�lenie, �e funkcja RenderScene b�dzie funkcj� zwrotn�
	// (callback function).  Bedzie ona wywo�ywana za ka�dym razem
	// gdy zajdzie potrzba przeryswania okna 

	glutReshapeFunc(ChangeSize);
	// Dla aktualnego okna ustala funkcj� zwrotn� odpowiedzialn�
	// zazmiany rozmiaru okna      

	glutIdleFunc(spinEgg);

	glutKeyboardFunc(keys);
	glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
	// Kolor czyszc�cy (wype�nienia okna) ustawiono na szary


	glEnable(GL_DEPTH_TEST);
	// W��czenie mechanizmu usuwania powierzchni niewidocznych

	glutMainLoop();
	// Funkcja uruchamia szkielet biblioteki GLUT

}

/*************************************************************************************/