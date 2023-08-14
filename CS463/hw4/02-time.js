const calculateTime = (date1, date2) => {
  // Given two dates, calculate and return the amount of time elapsed in years and months
  const d1 = new Date(date1);
  const d2 = new Date(date2);

  if(isNaN(d1) || isNaN(d2)) {
    return "Error: Invalid input provided.";
  }

  const years = Math.abs(d1.getUTCFullYear() - d2.getUTCFullYear());
  const months = Math.abs(d1.getUTCMonth() - d2.getUTCMonth());

  let result = "Time elapsed: ";

  if(years == 0 && months == 0) {
    result += "Less than one month";
  }

  if(years == 1) {
    result += "1 year";
  }
  else if(years > 1) {
    result += years + " years";
  }

  if(months > 0 && years > 0) {
    result += ", ";
  }

  if(months == 1) {
      result += "1 month";
  }
  else if(months > 1) {
    result += months + " months";
  }

  return result;
};

// Date() formats:
// new Date('December 1, 1995')
// new Date('2008-1-11')
// new Date(2020, 3, 16)
// new Date(628021800000)

console.log(calculateTime(1635176171332, 'May 1, 1995'));
// Time elapsed: 26 years, 5 months
console.log(calculateTime(1635176171332, '1975-8-11'));
// Time elapsed: 46 years, 2 months
console.log(calculateTime(1635176171332, [2021, 5, 23]));
// Time elapsed: 5 months
console.log(calculateTime(1635176171332, 1031814000000));
// Time elapsed: 19 years, 1 month
console.log(calculateTime(1635176171332, 'birthdate'));
// Error: Invalid input provided.
console.log(calculateTime(1635176171332, '1975-13-35'));
// Error: Invalid input provided.
console.log(calculateTime('May 1, 1995', 1635176171332));
// Time elapsed: 26 years, 5 months
console.log(calculateTime([2021, 5, 23], 1635176171332));
// Time elapsed: 5 months
console.log(calculateTime('birthdate', 1635176171332));
// Error: Invalid input provided.
console.log(calculateTime('1975-13-35', 1635176171332));
// Error: Invalid input provided.
console.log(calculateTime(1635176171332, 1635676171332));
// Time elapsed: Less than one month
