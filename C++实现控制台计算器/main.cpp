#include<iostream>
#include<string>
#include"subfunction.cpp"
#include"mainusefun.cpp"
using namespace std;
int main()
{
	cout << "��������ʽ��";
	char notionset[20] = {'0','1','2','3','4','5','6','7','8','9','+','-','*','/','%','^','=','(',')','.'};
	
	while(true)
	{
		string expression = "";
		char ch;
		while((ch=getchar())!= '\n')
		expression+=ch;
		if(othernotion(expression,notionset,20))
		{cout << "���ʽ���벻�Ϸ�������������:";
		continue;}
		
		else
			{//else1
				if( makeSense(expression) )//makesense�����жϱ��ʽ�Ƿ����
				{ 
					untobi(&expression);
					cout << calculate(expression) <<endl;//��������calculate����������ʽ 
			
					cout << "������y or n" << endl;
				
					char answer = getchar();
					//cin >> answer;
					char end = getchar();//�Ե��س� 
					
					if(answer == 'y')
					{cout << "��������ʽ��";
					continue;}
					else
					{
					cout << "����������û����..." << endl;
					break;}
					}
					else{cout << "���ʽ���벻�Ϸ������������룺";
					continue;} 
			}//else1
		
	}//while
	return 0;
}//main

