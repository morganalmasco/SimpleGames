#dictionaries
definitions = {'modest':'unassuming','eat':'chew and swallow'}
#               ^key(immutable)                 ^value(any type)
grades = {'John':95,'Abby':83,'Charlie':67}
grades['Charlie'] = 41
grades['Morgan'] = 100
print(grades)
openedFile = open("passingGrades.txt","w")
totalGrades = 0
for students in grades:
    totalGrades += grades[students]

average = totalGrades / (len(grades))
#print(average)

#print(grades.keys())
#print(grades.values())
#for name in grades.keys():
    #print(name)


grades = [95, 96, 100, 85, 95, 90, 95, 100, 100]
gradesDict = {}
for grade in grades:
    if grade not in gradesDict:
        gradesDict[grade] = 1
    else:
        gradesDict[grade] += 1
print(gradesDict)

words = "This is a string of a loooot of words! I love python."
vowelsUsed = {}
for char in words:
    char = char.lower()
    vowels = 'aeiou'
    if char in vowels:
        if char not in vowelsUsed:
            vowelsUsed[char] = 1
        else:
            vowelsUsed[char] += 1
#print(vowelsUsed)

wordsOccurences = [("rabbit",1),("Alice",1),("rabbit",4),("Alice",7),("Alice",10)]
wordsUsed = {}
for word in wordsOccurences:
    if word[0] not in wordsUsed:
        wordsUsed[word[0]] = [word[1]]
    else:
        wordsUsed[word[0]].append(word[1])
print(wordsUsed)
openedFile.close()