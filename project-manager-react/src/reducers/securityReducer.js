import { SET_CURRENT_USER } from "../actions/types";

const initialState = {
  valid_token: false,
  user: {},
};

const booleanActionPayload = (payload) => {
  if (payload) return true;
  else return false;
};

export default function (state = initialState, action) {
  switch (action.type) {
    case SET_CURRENT_USER:
      return {
        ...state,
        valid_token: booleanActionPayload(action.payload),
        user: action.payload,
      };

    default:
      return state;
  }
}
