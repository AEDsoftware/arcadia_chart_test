Here is an explanation of the settings:

source
	Chooses the source for the data.
	csv = a comma seperated file derived from Excel
	db = a database connection

max_ticks
	The limit of how many ticks (numbers on the horizontal axis) to display.
	A number from 1 to ...

chart_type
	The type of chart to be generated with the data.
	category_calls_time = shows the # of calls corresponding to each millisecond bucket.

trim
	Limits the highest time shown (for example, trim of 1000 shows times up to 1000 milliseconds). A trim of -1 shows all records, while a trim of 0 uses auto-trim.
	A number from 1 to ...

trim_threshold
	If trim = 0, the program will process the data starting at the end. All buckets with less than [trim_threshold] calls are trimmed off; once a bucket is reached with more calls than the threshold, the trim ends.
	A number from 1 to ...

service_list
	If services are listed here, only the listed services will be used to determine the data.
	A list of service names seperated by @

seperate_charts
	If set to true and more than one service is listed in service_list, each service will be output to a seperate chart
	"true" if desired

csv_input_file
	The name of a csv file containing appropriate input data. Used only if source = csv.
	A full file path ending in .csv.

jpeg_output_file
	The name of a jpeg file to output to. If multiple files are generated (see service_list and seperate_charts, above), each will have their service name appended before .jpeg. Used only if output = jpeg.
	A full file path ending in .jpeg.

jpeg_width
	The width of the output jpeg. Used only if output = jpeg.
	A number from 1 to ...

jpeg_height
	The height of the output jpeg. Used only if output = jpeg.
	A number from 1 to ...