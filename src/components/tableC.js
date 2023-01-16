import './style.css';

const TableC = () => {
    
    // get table_c by running dedup.java
    const table_c = [["836 Cornfield Dr", "Arlington", "TX"],
     ["819 Lovingham Dr", "Arlington", "TX"],
      ["5208 Rowcrop Dr", "Arlington", "TX"],
       ["905 Thistle Ridge Ct", "Arlington", "TX"],
        ["5826 Falconcrest Dr", "Arlington", "TX"],
         ["408 Valley Spring Dr", "Arlington", "TX"],
          ["815 Cornfield Dr", "Arlington", "TX"],
           ["306 Iberis Dr", "Arlington", "TX"],
           ["932 Danforth Pl", "Arlington", "TX"],
           ["205 Kalmia Dr", "Arlington", "TX"],
        ["406 Faircrest Dr", "Arlington", "TX"],
         ["305 Iberis Dr", "Arlington", "TX"]]

         return(
            <>
            
                <h3>Table A and B</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Address</th>
                            <th>City</th>
                            <th>State</th>
                        </tr>
                    </thead>
                    <tbody>
                        {table_c.map((row, index) => (
                                    <tr key={index}>
                                        <td>{row[0]}</td>
                                        <td>{row[1]}</td>
                                        <td>{row[2]}</td>
                                    </tr>
                                )
                            )}
    
                    </tbody>
               
            </table>
          </>
        )
}
export default TableC;