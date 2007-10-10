
insert into dialogix3.parsertest values ('test', 'test', 'test', 1, NULL, NULL);
insert into dialogix3.parsertest values ('parseExpr("Какова пола `(0)?\'Ваш старший ребенок?\':\'Вы бы хотели чтобы был первенец?\'`")', 'Какова пола Вы бы хотели чтобы был первенец?', 'Какова пола Вы бы хотели чтобы был первенец?', 1, NULL, NULL);
insert into dialogix3.parsertest values ('parseExpr("Как бы Вы хотели `(0)?\'его\':\'ее\'` назвать?")', 'Как бы Вы хотели ее назвать?', 'Как бы Вы хотели ее назвать?', 1, NULL, NULL);
