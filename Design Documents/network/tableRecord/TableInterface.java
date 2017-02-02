package network.tableRecord;

public interface TableInterface {

	List getTableArrayList();

	/**
	 * 
	 * @param TableRecord
	 */
	TableRecord addItem(int TableRecord);

	/**
	 * 
	 * @param TableRecord
	 */
	TableRecord getItem(int TableRecord);

	/**
	 * 
	 * @param key
	 */
	TableRecord removeItem(Integer key);

	/**
	 * 
	 * @param key
	 */
	TableRecord getItem(Integer key);

	Void clear();

}